package com.company.pm.interactionservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.Attachment;
import com.company.pm.interactionservice.domain.repositories.rowmapper.AttachmentRowMapper;
import com.company.pm.interactionservice.domain.repositories.rowmapper.CommentRowMapper;
import com.company.pm.interactionservice.domain.repositories.rowmapper.PostRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Attachment entity.
 */
@SuppressWarnings("unused")
class AttachmentRepositoryInternalImpl implements AttachmentRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PostRowMapper postMapper;
    private final CommentRowMapper commentMapper;
    private final AttachmentRowMapper attachmentMapper;

    private static final Table entityTable = Table.aliased("attachments", EntityManager.ENTITY_ALIAS);
    private static final Table postTable = Table.aliased("posts", "post");
    private static final Table commentTable = Table.aliased("comments", "e_comment");

    public AttachmentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PostRowMapper postMapper,
        CommentRowMapper commentMapper,
        AttachmentRowMapper attachmentMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public Flux<Attachment> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Attachment> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Attachment> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = AttachmentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PostSqlHelper.getColumns(postTable, "post"));
        columns.addAll(CommentSqlHelper.getColumns(commentTable, "comment"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(postTable)
            .on(Column.create("post_id", entityTable))
            .equals(Column.create("id", postTable))
            .leftOuterJoin(commentTable)
            .on(Column.create("comment_id", entityTable))
            .equals(Column.create("id", commentTable));

        String select = entityManager.createSelect(selectFrom, Attachment.class, pageable, criteria);
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
    public Flux<Attachment> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Attachment> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Attachment process(Row row, RowMetadata metadata) {
        Attachment entity = attachmentMapper.apply(row, "e");
        entity.setPost(postMapper.apply(row, "post"));
        entity.setComment(commentMapper.apply(row, "comment"));
        return entity;
    }

    @Override
    public <S extends Attachment> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Attachment> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Attachment with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Attachment entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class AttachmentSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("thumb_url", table, columnPrefix + "_thumb_url"));
        columns.add(Column.aliased("file_url", table, columnPrefix + "_file_url"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));

        columns.add(Column.aliased("post_id", table, columnPrefix + "_post_id"));
        columns.add(Column.aliased("comment_id", table, columnPrefix + "_comment_id"));
        return columns;
    }
}
