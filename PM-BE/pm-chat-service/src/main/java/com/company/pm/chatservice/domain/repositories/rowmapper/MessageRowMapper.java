package com.company.pm.chatservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.chatservice.Message;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Message}, with proper type conversions.
 */
@Service
public class MessageRowMapper implements BiFunction<Row, String, Message> {

    private final ColumnConverter converter;

    public MessageRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Message} stored in the database.
     */
    @Override
    public Message apply(Row row, String prefix) {
        Message entity = Message.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setContent(converter.fromRow(row, prefix + "_content", String.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setDeletedAt(converter.fromRow(row, prefix + "_deleted_at", Instant.class));
        entity.setConversationId(converter.fromRow(row, prefix + "_conversation_id", Long.class));
        entity.setSenderId(converter.fromRow(row, prefix + "_sender_id", String.class));
        return entity;
    }
}
