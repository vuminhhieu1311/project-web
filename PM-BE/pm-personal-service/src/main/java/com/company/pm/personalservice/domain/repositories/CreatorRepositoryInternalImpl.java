package com.company.pm.personalservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.Creator;
import com.company.pm.personalservice.domain.repositories.rowmapper.CreatorRowMapper;
import com.company.pm.personalservice.domain.repositories.rowmapper.ProjectRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Creator entity.
 */
@SuppressWarnings("unused")
class CreatorRepositoryInternalImpl implements CreatorRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProjectRowMapper projectMapper;
    private final CreatorRowMapper creatorMapper;

    private static final Table entityTable = Table.aliased("creators", EntityManager.ENTITY_ALIAS);
    private static final Table projectTable = Table.aliased("projects", "project");

    public CreatorRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProjectRowMapper projectMapper,
        CreatorRowMapper creatorMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.projectMapper = projectMapper;
        this.creatorMapper = creatorMapper;
    }

    @Override
    public Flux<Creator> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Creator> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Creator> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = CreatorSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ProjectSqlHelper.getColumns(projectTable, "project"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(projectTable)
            .on(Column.create("project_id", entityTable))
            .equals(Column.create("id", projectTable));

        String select = entityManager.createSelect(selectFrom, Creator.class, pageable, criteria);
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
    public Flux<Creator> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Creator> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Creator process(Row row, RowMetadata metadata) {
        Creator entity = creatorMapper.apply(row, "e");
        entity.setProject(projectMapper.apply(row, "project"));
        return entity;
    }

    @Override
    public <S extends Creator> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Creator> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Creator with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Creator entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class CreatorSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));

        columns.add(Column.aliased("project_id", table, columnPrefix + "_project_id"));
        return columns;
    }
}
