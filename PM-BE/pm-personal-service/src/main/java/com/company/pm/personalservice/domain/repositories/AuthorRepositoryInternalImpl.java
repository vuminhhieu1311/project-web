package com.company.pm.personalservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.Author;
import com.company.pm.personalservice.domain.repositories.rowmapper.AuthorRowMapper;
import com.company.pm.personalservice.domain.repositories.rowmapper.PublicationRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
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
 * Spring Data SQL reactive custom repository implementation for the Author entity.
 */
@SuppressWarnings("unused")
class AuthorRepositoryInternalImpl implements AuthorRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PublicationRowMapper publicationMapper;
    private final AuthorRowMapper authorMapper;

    private static final Table entityTable = Table.aliased("authors", EntityManager.ENTITY_ALIAS);
    private static final Table publicationTable = Table.aliased("publications", "publication");

    public AuthorRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PublicationRowMapper publicationMapper,
        AuthorRowMapper authorMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.publicationMapper = publicationMapper;
        this.authorMapper = authorMapper;
    }

    @Override
    public Flux<Author> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Author> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Author> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = AuthorSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PublicationSqlHelper.getColumns(publicationTable, "publication"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(publicationTable)
            .on(Column.create("publication_id", entityTable))
            .equals(Column.create("id", publicationTable));

        String select = entityManager.createSelect(selectFrom, Author.class, pageable, criteria);
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
    public Flux<Author> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Author> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Author process(Row row, RowMetadata metadata) {
        Author entity = authorMapper.apply(row, "e");
        entity.setPublication(publicationMapper.apply(row, "publication"));
        return entity;
    }

    @Override
    public <S extends Author> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Author> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Author with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Author entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class AuthorSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));

        columns.add(Column.aliased("publication_id", table, columnPrefix + "_publication_id"));
        return columns;
    }
}
