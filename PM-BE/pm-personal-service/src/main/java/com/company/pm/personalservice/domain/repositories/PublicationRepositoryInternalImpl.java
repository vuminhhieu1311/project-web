package com.company.pm.personalservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.domain.repositories.rowmapper.PersonalProfileRowMapper;
import com.company.pm.personalservice.domain.repositories.rowmapper.PublicationRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Publication entity.
 */
@SuppressWarnings("unused")
class PublicationRepositoryInternalImpl implements PublicationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PersonalProfileRowMapper personalprofileMapper;
    private final PublicationRowMapper publicationMapper;

    private static final Table entityTable = Table.aliased("publications", EntityManager.ENTITY_ALIAS);
    private static final Table personalProfileTable = Table.aliased("personal_profiles", "personalProfile");

    public PublicationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PersonalProfileRowMapper personalprofileMapper,
        PublicationRowMapper publicationMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.personalprofileMapper = personalprofileMapper;
        this.publicationMapper = publicationMapper;
    }

    @Override
    public Flux<Publication> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Publication> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<Publication> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = PublicationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PersonalProfileSqlHelper.getColumns(personalProfileTable, "personalProfile"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(personalProfileTable)
            .on(Column.create("personal_profile_id", entityTable))
            .equals(Column.create("id", personalProfileTable));

        String select = entityManager.createSelect(selectFrom, Publication.class, pageable, criteria);
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
    public Flux<Publication> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Publication> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Publication process(Row row, RowMetadata metadata) {
        Publication entity = publicationMapper.apply(row, "e");
        entity.setPersonalProfile(personalprofileMapper.apply(row, "personalProfile"));
        return entity;
    }

    @Override
    public <S extends Publication> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Publication> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Publication with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Publication entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class PublicationSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("publisher", table, columnPrefix + "_publisher"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("publication_date", table, columnPrefix + "_publication_date"));
        columns.add(Column.aliased("url", table, columnPrefix + "_url"));

        columns.add(Column.aliased("personal_profile_id", table, columnPrefix + "_personal_profile_id"));
        return columns;
    }
}
