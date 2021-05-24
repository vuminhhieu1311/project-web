package com.company.pm.personalservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.domain.repositories.rowmapper.PersonalProfileRowMapper;
import com.company.pm.personalservice.domain.repositories.rowmapper.WorkExperienceRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the WorkExperience entity.
 */
@SuppressWarnings("unused")
class WorkExperienceRepositoryInternalImpl implements WorkExperienceRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PersonalProfileRowMapper personalprofileMapper;
    private final WorkExperienceRowMapper workexperienceMapper;

    private static final Table entityTable = Table.aliased("work_experiences", EntityManager.ENTITY_ALIAS);
    private static final Table personalProfileTable = Table.aliased("personal_profiles", "personalProfile");

    public WorkExperienceRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PersonalProfileRowMapper personalprofileMapper,
        WorkExperienceRowMapper workexperienceMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.personalprofileMapper = personalprofileMapper;
        this.workexperienceMapper = workexperienceMapper;
    }

    @Override
    public Flux<WorkExperience> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<WorkExperience> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<WorkExperience> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = WorkExperienceSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PersonalProfileSqlHelper.getColumns(personalProfileTable, "personalProfile"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(personalProfileTable)
            .on(Column.create("personal_profile_id", entityTable))
            .equals(Column.create("id", personalProfileTable));

        String select = entityManager.createSelect(selectFrom, WorkExperience.class, pageable, criteria);
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
    public Flux<WorkExperience> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<WorkExperience> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private WorkExperience process(Row row, RowMetadata metadata) {
        WorkExperience entity = workexperienceMapper.apply(row, "e");
        entity.setPersonalProfile(personalprofileMapper.apply(row, "personalProfile"));
        return entity;
    }

    @Override
    public <S extends WorkExperience> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends WorkExperience> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update WorkExperience with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(WorkExperience entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class WorkExperienceSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("employment_type", table, columnPrefix + "_employment_type"));
        columns.add(Column.aliased("location", table, columnPrefix + "_location"));
        columns.add(Column.aliased("start_date", table, columnPrefix + "_start_date"));
        columns.add(Column.aliased("end_date", table, columnPrefix + "_end_date"));
        columns.add(Column.aliased("personal_profile_id", table, columnPrefix + "_personal_profile_id"));
        return columns;
    }
}
