package com.company.pm.chatservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.chatservice.Conversation;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Conversation}, with proper type conversions.
 */
@Service
public class ConversationRowMapper implements BiFunction<Row, String, Conversation> {

    private final ColumnConverter converter;

    public ConversationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Conversation} stored in the database.
     */
    @Override
    public Conversation apply(Row row, String prefix) {
        Conversation entity = Conversation.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setUpdatedAt(converter.fromRow(row, prefix + "_updated_at", Instant.class));
        entity.setCreatorId(converter.fromRow(row, prefix + "_creator_id", String.class));
        return entity;
    }
}
