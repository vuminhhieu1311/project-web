package com.company.pm.interactionservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.Attachment;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Attachment}, with proper type conversions.
 */
@Service
public class AttachmentRowMapper implements BiFunction<Row, String, Attachment> {

    private final ColumnConverter converter;

    public AttachmentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Attachment} stored in the database.
     */
    @Override
    public Attachment apply(Row row, String prefix) {
        Attachment entity = Attachment.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setThumbUrl(converter.fromRow(row, prefix + "_thumb_url", String.class));
        entity.setFileUrl(converter.fromRow(row, prefix + "_file_url", String.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setPostId(converter.fromRow(row, prefix + "_post_id", Long.class));
        entity.setCommentId(converter.fromRow(row, prefix + "_comment_id", Long.class));
        return entity;
    }
}
