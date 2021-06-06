package com.company.pm.interactionservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.interactionservice.Like;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Like}, with proper type conversions.
 */
@Service
public class LikeRowMapper implements BiFunction<Row, String, Like> {

    private final ColumnConverter converter;

    public LikeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Like} stored in the database.
     */
    @Override
    public Like apply(Row row, String prefix) {
        Like entity = Like.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setPostId(converter.fromRow(row, prefix + "_post_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        return entity;
    }
}
