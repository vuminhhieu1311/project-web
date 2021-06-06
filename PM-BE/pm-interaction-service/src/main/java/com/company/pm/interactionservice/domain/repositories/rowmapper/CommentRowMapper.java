package com.company.pm.interactionservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.interactionservice.Comment;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Comment}, with proper type conversions.
 */
@Service
public class CommentRowMapper implements BiFunction<Row, String, Comment> {

    private final ColumnConverter converter;

    public CommentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Comment} stored in the database.
     */
    @Override
    public Comment apply(Row row, String prefix) {
        Comment entity = Comment.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setContent(converter.fromRow(row, prefix + "_content", String.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setUpdatedAt(converter.fromRow(row, prefix + "_updated_at", Instant.class));
        entity.setCompanyId(converter.fromRow(row, prefix + "_company_id", Long.class));
        entity.setParentCommentId(converter.fromRow(row, prefix + "_parent_comment_id", Long.class));
        entity.setPostId(converter.fromRow(row, prefix + "_post_id", Long.class));
        entity.setAuthorId(converter.fromRow(row, prefix + "_author_id", String.class));
        return entity;
    }
}
