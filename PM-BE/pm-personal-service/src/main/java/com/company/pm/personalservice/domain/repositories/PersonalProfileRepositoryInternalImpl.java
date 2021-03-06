package com.company.pm.personalservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.repositories.rowmapper.PersonalProfileRowMapper;
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
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the PersonalProfile entity.
 */
@SuppressWarnings("unused")
class PersonalProfileRepositoryInternalImpl implements PersonalProfileRepositoryInternal {
    
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;
    
    private final UserRowMapper userMapper;
    private final PersonalProfileRowMapper personalprofileMapper;
    
    private static final Table entityTable = Table.aliased("personal_profiles", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("users", "e_user");
    
    public PersonalProfileRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        PersonalProfileRowMapper personalprofileMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.personalprofileMapper = personalprofileMapper;
    }
    
    @Override
    public Flux<PersonalProfile> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }
    
    @Override
    public Flux<PersonalProfile> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    @Override
    public Mono<PersonalProfile> findOneBy(Criteria criteria) {
        return createQuery(null, criteria).one();
    }
    
    RowsFetchSpec<PersonalProfile> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = PersonalProfileSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable));
        
        String select = entityManager.createSelect(selectFrom, PersonalProfile.class, pageable, criteria);
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
    public Flux<PersonalProfile> findAll() {
        return findAllBy(null, null);
    }
    
    @Override
    public Mono<PersonalProfile> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }
    
    private PersonalProfile process(Row row, RowMetadata metadata) {
        PersonalProfile entity = personalprofileMapper.apply(row, "e");
        entity.setUser(userMapper.apply(row, "user"));
        return entity;
    }
    
    @Override
    public <S extends PersonalProfile> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }
    
    @Override
    public <S extends PersonalProfile> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update PersonalProfile with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }
    
    @Override
    public Mono<Integer> update(PersonalProfile entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class PersonalProfileSqlHelper {
    
    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("birthday", table, columnPrefix + "_birthday"));
        columns.add(Column.aliased("country", table, columnPrefix + "_country"));
        columns.add(Column.aliased("location", table, columnPrefix + "_location"));
        columns.add(Column.aliased("phone_number", table, columnPrefix + "_phone_number"));
        columns.add(Column.aliased("address", table, columnPrefix + "_address"));
        columns.add(Column.aliased("about", table, columnPrefix + "_about"));
        columns.add(Column.aliased("headline", table, columnPrefix + "_headline"));
        columns.add(Column.aliased("industry", table, columnPrefix + "_industry"));
        columns.add(Column.aliased("bg_image_url", table, columnPrefix + "_bg_image_url"));
        
        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        return columns;
    }
}
