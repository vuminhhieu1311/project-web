package com.company.pm.interactionservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.companyservice.domain.repositories.CompanySqlHelper;
import com.company.pm.companyservice.domain.repositories.rowmapper.CompanyRowMapper;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.interactionservice.domain.repositories.rowmapper.CommentRowMapper;
import com.company.pm.interactionservice.domain.repositories.rowmapper.PostRowMapper;
import com.company.pm.userservice.domain.repositories.UserSqlHelper;
import com.company.pm.userservice.domain.repositories.rowmapper.UserRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the Comment entity.
 */
@SuppressWarnings("unused")
class CommentRepositoryInternalImpl implements CommentRepositoryInternal {
    
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;
    
    private final CompanyRowMapper companyMapper;
    private final CommentRowMapper commentMapper;
    private final PostRowMapper postMapper;
    private final UserRowMapper userMapper;
    
    private static final Table entityTable = Table.aliased("comments", EntityManager.ENTITY_ALIAS);
    private static final Table companyTable = Table.aliased("companies", "company");
    private static final Table parentCommentTable = Table.aliased("comments", "parentComment");
    private static final Table postTable = Table.aliased("posts", "post");
    private static final Table authorTable = Table.aliased("users", "author");
    
    public CommentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CompanyRowMapper companyMapper,
        CommentRowMapper commentMapper,
        PostRowMapper postMapper,
        UserRowMapper userMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.companyMapper = companyMapper;
        this.commentMapper = commentMapper;
        this.postMapper = postMapper;
        this.userMapper = userMapper;
    }
    
    @Override
    public Flux<Comment> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }
    
    @Override
    public Flux<Comment> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    @Override
    public Mono<Comment> findOneBy(Criteria criteria) {
        return createQuery(null, criteria).one();
    }
    
    RowsFetchSpec<Comment> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = CommentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(CompanySqlHelper.getColumns(companyTable, "company"));
        columns.addAll(CommentSqlHelper.getColumns(parentCommentTable, "parentComment"));
        columns.addAll(PostSqlHelper.getColumns(postTable, "post"));
        columns.addAll(UserSqlHelper.getColumns(authorTable, "author"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(companyTable)
            .on(Column.create("company_id", entityTable))
            .equals(Column.create("id", companyTable))
            .leftOuterJoin(parentCommentTable)
            .on(Column.create("parent_comment_id", entityTable))
            .equals(Column.create("id", parentCommentTable))
            .leftOuterJoin(postTable)
            .on(Column.create("post_id", entityTable))
            .equals(Column.create("id", postTable))
            .leftOuterJoin(authorTable)
            .on(Column.create("author_id", entityTable))
            .equals(Column.create("id", authorTable));
        
        String select = entityManager.createSelect(selectFrom, Comment.class, pageable, criteria);
        String alias = entityTable.getReferenceName().getReference();
        String selectWhere = Optional
            .ofNullable(criteria)
            .map(
                crit ->
                    new StringBuilder(select)
                        .append(" ")
                        .append("WHERE")
                        .append(" ")
                        .append(alias)
                        .append(".")
                        .append(crit.toString())
                        .toString()
            )
            .orElse(select); // TODO remove once https://github.com/spring-projects/spring-data-jdbc/issues/907 will be fixed
        return db.sql(selectWhere).map(this::process);
    }
    
    @Override
    public Flux<Comment> findAll() {
        return findAllBy(null, null);
    }
    
    @Override
    public Mono<Comment> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }
    
    private Comment process(Row row, RowMetadata metadata) {
        Comment entity = commentMapper.apply(row, "e");
        entity.setCompany(companyMapper.apply(row, "company"));
        entity.setParentComment(commentMapper.apply(row, "parentComment"));
        entity.setPost(postMapper.apply(row, "post"));
        entity.setAuthor(userMapper.apply(row, "author"));
        return entity;
    }
    
    @Override
    public <S extends Comment> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }
    
    @Override
    public <S extends Comment> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Comment with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }
    
    @Override
    public Mono<Integer> update(Comment entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class CommentSqlHelper {
    
    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("content", table, columnPrefix + "_content"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));
        columns.add(Column.aliased("updated_at", table, columnPrefix + "_updated_at"));
        
        columns.add(Column.aliased("company_id", table, columnPrefix + "_company_id"));
        columns.add(Column.aliased("parent_comment_id", table, columnPrefix + "_parent_comment_id"));
        columns.add(Column.aliased("post_id", table, columnPrefix + "_post_id"));
        columns.add(Column.aliased("author_id", table, columnPrefix + "_author_id"));
        return columns;
    }
}

