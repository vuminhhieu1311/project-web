package com.company.pm.interactionservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.interactionservice.CommentLike;
import com.company.pm.interactionservice.domain.repositories.rowmapper.CommentLikeRowMapper;
import com.company.pm.interactionservice.domain.repositories.rowmapper.CommentRowMapper;
import com.company.pm.userservice.domain.repositories.UserSqlHelper;
import com.company.pm.userservice.domain.repositories.rowmapper.UserRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * Spring Data SQL reactive custom repository implementation for the CommentLike entity.
 */
@SuppressWarnings("unused")
class CommentLikeRepositoryInternalImpl implements CommentLikeRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final CommentRowMapper commentMapper;
    private final CommentLikeRowMapper commentlikeMapper;

    private static final Table entityTable = Table.aliased("comment_likes", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("users", "e_user");
    private static final Table commentTable = Table.aliased("comments", "e_comment");

    public CommentLikeRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        CommentRowMapper commentMapper,
        CommentLikeRowMapper commentlikeMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.commentlikeMapper = commentlikeMapper;
    }

    @Override
    public Flux<CommentLike> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<CommentLike> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    @Override
    public Mono<CommentLike> findOneBy(Criteria criteria) {
        return createQuery(null, criteria).one();
    }
    
    RowsFetchSpec<CommentLike> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = CommentLikeSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        columns.addAll(CommentSqlHelper.getColumns(commentTable, "comment"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable))
            .leftOuterJoin(commentTable)
            .on(Column.create("comment_id", entityTable))
            .equals(Column.create("id", commentTable));

        String select = entityManager.createSelect(selectFrom, CommentLike.class, pageable, criteria);
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
    public Flux<CommentLike> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<CommentLike> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private CommentLike process(Row row, RowMetadata metadata) {
        CommentLike entity = commentlikeMapper.apply(row, "e");
        entity.setUser(userMapper.apply(row, "user"));
        entity.setComment(commentMapper.apply(row, "comment"));
        return entity;
    }

    @Override
    public <S extends CommentLike> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends CommentLike> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update CommentLike with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(CommentLike entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class CommentLikeSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));

        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        columns.add(Column.aliased("comment_id", table, columnPrefix + "_comment_id"));
        return columns;
    }
}
