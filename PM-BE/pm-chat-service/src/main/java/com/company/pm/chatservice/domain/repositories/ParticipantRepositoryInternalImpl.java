package com.company.pm.chatservice.domain.repositories;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.company.pm.chatservice.domain.repositories.rowmapper.ConversationRowMapper;
import com.company.pm.chatservice.domain.repositories.rowmapper.ParticipantRowMapper;
import com.company.pm.common.services.EntityManager;
import com.company.pm.domain.chatservice.Participant;
import com.company.pm.userservice.domain.repositories.UserSqlHelper;
import com.company.pm.userservice.domain.repositories.rowmapper.UserRowMapper;
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
 * Spring Data SQL reactive custom repository implementation for the Participant entity.
 */
@SuppressWarnings("unused")
class ParticipantRepositoryInternalImpl implements ParticipantRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UserRowMapper userMapper;
    private final ConversationRowMapper conversationMapper;
    private final ParticipantRowMapper participantMapper;

    private static final Table entityTable = Table.aliased("participants", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("users", "e_user");
    private static final Table conversationTable = Table.aliased("conversations", "conversation");

    public ParticipantRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UserRowMapper userMapper,
        ConversationRowMapper conversationMapper,
        ParticipantRowMapper participantMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.userMapper = userMapper;
        this.conversationMapper = conversationMapper;
        this.participantMapper = participantMapper;
    }

    @Override
    public Flux<Participant> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<Participant> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }
    
    RowsFetchSpec<Participant> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = ParticipantSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        columns.addAll(ConversationSqlHelper.getColumns(conversationTable, "conversation"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable))
            .leftOuterJoin(conversationTable)
            .on(Column.create("conversation_id", entityTable))
            .equals(Column.create("id", conversationTable));

        String select = entityManager.createSelect(selectFrom, Participant.class, pageable, criteria);
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
    public Flux<Participant> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<Participant> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private Participant process(Row row, RowMetadata metadata) {
        Participant entity = participantMapper.apply(row, "e");
        entity.setUser(userMapper.apply(row, "user"));
        entity.setConversation(conversationMapper.apply(row, "conversation"));
        return entity;
    }

    @Override
    public <S extends Participant> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends Participant> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update Participant with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(Participant entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class ParticipantSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("created_at", table, columnPrefix + "_created_at"));
        columns.add(Column.aliased("updated_at", table, columnPrefix + "_updated_at"));

        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        columns.add(Column.aliased("conversation_id", table, columnPrefix + "_conversation_id"));
        return columns;
    }
}
