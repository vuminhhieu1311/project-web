package com.company.pm.personalservice.domain.repositories;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.domain.repositories.rowmapper.EducationRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.relational.core.query.Criteria.where;

/**
 * Spring Data SQL reactive custom repository implementation for the Education entity.
 */
@SuppressWarnings("unused")
class EducationRepositoryInternalImpl implements EducationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final EducationRowMapper educationMapper;

    private static final Table entityTable = Table.aliased("educations", EntityManager.ENTITY_ALIAS);

    public EducationRepositoryInternalImpl(R2dbcEntityTemplate template, EntityManager entityManager, EducationRowMapper educationMapper) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.educationMapper = educationMapper;
    }

    @Override
    public Flux<Education> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Education> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Education> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = EducationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);

        String select = entityManager.createSelect(selectFrom, Education.class, pageable, criteria);
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
    public Flux<Education> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Education> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Education process(Row row, RowMetadata metadata) {
        Education entity = educationMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Education> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Education> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Education with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Education entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class EducationSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("school", table, columnPrefix + "_school"));
        columns.add(Column.aliased("degree", table, columnPrefix + "_degree"));
        columns.add(Column.aliased("field_of_study", table, columnPrefix + "_field_of_study"));
        columns.add(Column.aliased("start_date", table, columnPrefix + "_start_date"));
        columns.add(Column.aliased("end_date", table, columnPrefix + "_end_date"));
        columns.add(Column.aliased("grade", table, columnPrefix + "_grade"));
        columns.add(Column.aliased("activities", table, columnPrefix + "_activities"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));

        return columns;
    }
}
