package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.Certification;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Certification}, with proper type conversions.
 */
@Service
public class CertificationRowMapper implements BiFunction<Row, String, Certification> {

    private final ColumnConverter converter;

    public CertificationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Certification} stored in the database.
     */
    @Override
    public Certification apply(Row row, String prefix) {
        Certification entity = Certification.builder().build();
        
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setPersonalProfileId(converter.fromRow(row, prefix + "_personal_profile_id", Long.class));
        
        return entity;
    }
}
