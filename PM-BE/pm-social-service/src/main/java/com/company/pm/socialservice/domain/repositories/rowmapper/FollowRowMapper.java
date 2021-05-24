package com.company.pm.socialservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.socialservice.Follow;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Follow}, with proper type conversions.
 */
@Service
public class FollowRowMapper implements BiFunction<Row, String, Follow> {

    private final ColumnConverter converter;

    public FollowRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Follow} stored in the database.
     */
    @Override
    public Follow apply(Row row, String prefix) {
        Follow entity = Follow.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFollowerId(converter.fromRow(row, prefix + "_follower_id", String.class));
        entity.setFollowedId(converter.fromRow(row, prefix + "_followed_id", String.class));
        return entity;
    }
}
