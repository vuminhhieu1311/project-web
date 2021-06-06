package com.company.pm.interactionservice.domain.repositories.rowmapper;

import com.company.pm.common.enumeration.Visionable;
import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.interactionservice.Post;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Post}, with proper type conversions.
 */
@Service
public class PostRowMapper implements BiFunction<Row, String, Post> {

    private final ColumnConverter converter;

    public PostRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Post} stored in the database.
     */
    @Override
    public Post apply(Row row, String prefix) {
        Post entity = Post.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setContent(converter.fromRow(row, prefix + "_content", String.class));
        entity.setVisionable(converter.fromRow(row, prefix + "_visionable", Visionable.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setUpdatedAt(converter.fromRow(row, prefix + "_updated_at", Instant.class));
        entity.setAuthorId(converter.fromRow(row, prefix + "_author_id", String.class));
        entity.setCompanyId(converter.fromRow(row, prefix + "_company_id", Long.class));
        return entity;
    }
}
