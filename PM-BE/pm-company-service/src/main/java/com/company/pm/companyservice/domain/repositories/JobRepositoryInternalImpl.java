package com.company.pm.companyservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.companyservice.domain.repositories.rowmapper.CompanyRowMapper;
import com.company.pm.companyservice.domain.repositories.rowmapper.JobRowMapper;
import com.company.pm.domain.companyservice.Job;
import com.company.pm.userservice.domain.repositories.UserSqlHelper;
import com.company.pm.userservice.domain.repositories.rowmapper.UserRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Job entity.
 */
@SuppressWarnings("unused")
class JobRepositoryInternalImpl implements JobRepositoryInternal {
    
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;
    
    private final CompanyRowMapper companyMapper;
    private final UserRowMapper userMapper;
    private final JobRowMapper jobMapper;
    
    private static final Table entityTable = Table.aliased("jobs", EntityManager.ENTITY_ALIAS);
    private static final Table companyTable = Table.aliased("companies", "company");
    private static final Table posterTable = Table.aliased("users", "poster");
    
    public JobRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CompanyRowMapper companyMapper,
        UserRowMapper userMapper,
        JobRowMapper jobMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.companyMapper = companyMapper;
        this.userMapper = userMapper;
        this.jobMapper = jobMapper;
    }
    
    @Override
    public Flux<Job> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }
    
    @Override
    public Flux<Job> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    @Override
    public Mono<Job> findOneBy(Criteria criteria) {
        return createQuery(null, criteria).one();
    }
    
    RowsFetchSpec<Job> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = JobSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(CompanySqlHelper.getColumns(companyTable, "company"));
        columns.addAll(UserSqlHelper.getColumns(posterTable, "poster"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(companyTable)
            .on(Column.create("company_id", entityTable))
            .equals(Column.create("id", companyTable))
            .leftOuterJoin(posterTable)
            .on(Column.create("poster_id", entityTable))
            .equals(Column.create("id", posterTable));
        
        String select = entityManager.createSelect(selectFrom, Job.class, pageable, criteria);
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
    public Flux<Job> findAll() {
        return findAllBy(null, null);
    }
    
    @Override
    public Mono<Job> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }
    
    private Job process(Row row, RowMetadata metadata) {
        Job entity = jobMapper.apply(row, "e");
        entity.setCompany(companyMapper.apply(row, "company"));
        entity.setPoster(userMapper.apply(row, "poster"));
        return entity;
    }
    
    @Override
    public <S extends Job> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }
    
    @Override
    public <S extends Job> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Job with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }
    
    @Override
    public Mono<Integer> update(Job entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class JobSqlHelper {
    
    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("location", table, columnPrefix + "_location"));
        columns.add(Column.aliased("job_type", table, columnPrefix + "_job_type"));
        columns.add(Column.aliased("contact_email", table, columnPrefix + "_contact_email"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));
        columns.add(Column.aliased("closed_at", table, columnPrefix + "_closed_at"));
        columns.add(Column.aliased("activated", table, columnPrefix + "_activated"));
        
        columns.add(Column.aliased("company_id", table, columnPrefix + "_company_id"));
        columns.add(Column.aliased("poster_id", table, columnPrefix + "_poster_id"));
        return columns;
    }
}
