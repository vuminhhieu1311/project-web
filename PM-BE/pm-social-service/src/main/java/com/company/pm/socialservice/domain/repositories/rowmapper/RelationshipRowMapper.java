package com.company.pm.socialservice.domain.repositories.rowmapper;

import com.company.pm.common.enumeration.RelStatus;
import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.socialservice.Relationship;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Relationship}, with proper type conversions.
 */
@Service
public class RelationshipRowMapper implements BiFunction<Row, String, Relationship> {

    private final ColumnConverter converter;

    public RelationshipRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Relationship} stored in the database.
     */
    @Override
    public Relationship apply(Row row, String prefix) {
        Relationship entity = Relationship.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setRequesterId(converter.fromRow(row, prefix + "_requester_id", String.class));
        entity.setAddresseeId(converter.fromRow(row, prefix + "_addressee_id", String.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", RelStatus.class));
        entity.setPerformerId(converter.fromRow(row, prefix + "_performer_id", String.class));
        return entity;
    }
}
