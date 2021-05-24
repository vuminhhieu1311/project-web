package com.company.pm.chatservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.chatservice.Participant;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Participant}, with proper type conversions.
 */
@Service
public class ParticipantRowMapper implements BiFunction<Row, String, Participant> {

    private final ColumnConverter converter;

    public ParticipantRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Participant} stored in the database.
     */
    @Override
    public Participant apply(Row row, String prefix) {
        Participant entity = Participant.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setUpdatedAt(converter.fromRow(row, prefix + "_updated_at", Instant.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        entity.setConversationId(converter.fromRow(row, prefix + "_conversation_id", Long.class));
        return entity;
    }
}
