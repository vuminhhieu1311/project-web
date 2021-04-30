package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.Education;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Education}, with proper type conversions.
 */
@Service
public class EducationRowMapper implements BiFunction<Row, String, Education> {

    private final ColumnConverter converter;

    public EducationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Education} stored in the database.
     */
    @Override
    public Education apply(Row row, String prefix) {
        Education entity = Education.builder().build();
        
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setSchool(converter.fromRow(row, prefix + "_school", String.class));
        entity.setDegree(converter.fromRow(row, prefix + "_degree", String.class));
        entity.setFieldOfStudy(converter.fromRow(row, prefix + "_field_of_study", String.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", Instant.class));
        entity.setEndDate(converter.fromRow(row, prefix + "_end_date", Instant.class));
        entity.setGrade(converter.fromRow(row, prefix + "_grade", String.class));
        entity.setActivities(converter.fromRow(row, prefix + "_activities", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        
        return entity;
    }
}
