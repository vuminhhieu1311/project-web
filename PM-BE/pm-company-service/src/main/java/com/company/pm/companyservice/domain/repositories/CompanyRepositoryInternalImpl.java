package com.company.pm.companyservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.companyservice.domain.repositories.CompanyRepositoryInternal;
import com.company.pm.companyservice.domain.repositories.rowmapper.CompanyRowMapper;
import com.company.pm.domain.companyservice.Company;
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
 * Spring Data SQL reactive custom repository implementation for the Company entity.
 */
@SuppressWarnings("unused")
class CompanyRepositoryInternalImpl implements CompanyRepositoryInternal {
    
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;
    
    private final UserRowMapper userMapper;
    private final CompanyRowMapper companyMapper;
    
    private static final Table entityTable = Table.aliased("companies", EntityManager.ENTITY_ALIAS);
    private static final Table adminTable = Table.aliased("users", "e_admin");
    
    public CompanyRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        CompanyRowMapper companyMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
    }
    
    @Override
    public Flux<Company> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }
    
    @Override
    public Flux<Company> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    RowsFetchSpec<Company> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = CompanySqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(adminTable, "admin"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(adminTable)
            .on(Column.create("admin_id", entityTable))
            .equals(Column.create("id", adminTable));
        
        String select = entityManager.createSelect(selectFrom, Company.class, pageable, criteria);
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
    public Flux<Company> findAll() {
        return findAllBy(null, null);
    }
    
    @Override
    public Mono<Company> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }
    
    private Company process(Row row, RowMetadata metadata) {
        Company entity = companyMapper.apply(row, "e");
        entity.setAdmin(userMapper.apply(row, "admin"));
        return entity;
    }
    
    @Override
    public <S extends Company> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }
    
    @Override
    public <S extends Company> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Company with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }
    
    @Override
    public Mono<Integer> update(Company entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class CompanySqlHelper {
    
    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("website", table, columnPrefix + "_website"));
        columns.add(Column.aliased("industry", table, columnPrefix + "_industry"));
        columns.add(Column.aliased("company_size", table, columnPrefix + "_company_size"));
        columns.add(Column.aliased("company_type", table, columnPrefix + "_company_type"));
        columns.add(Column.aliased("logo_url", table, columnPrefix + "_logo_url"));
        columns.add(Column.aliased("tagline", table, columnPrefix + "_tagline"));
        
        columns.add(Column.aliased("admin_id", table, columnPrefix + "_admin_id"));
        return columns;
    }
}
