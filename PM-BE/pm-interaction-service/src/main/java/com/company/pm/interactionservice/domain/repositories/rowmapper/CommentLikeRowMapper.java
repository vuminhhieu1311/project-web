package com.company.pm.interactionservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.interactionservice.CommentLike;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CommentLike}, with proper type conversions.
 */
@Service
public class CommentLikeRowMapper implements BiFunction<Row, String, CommentLike> {

    private final ColumnConverter converter;

    public CommentLikeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CommentLike} stored in the database.
     */
    @Override
    public CommentLike apply(Row row, String prefix) {
        CommentLike entity = CommentLike.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        entity.setCommentId(converter.fromRow(row, prefix + "_comment_id", Long.class));
        return entity;
    }
}
