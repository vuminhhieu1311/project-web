package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.enumeration.EmploymentType;
import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.WorkExperience;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link WorkExperience}, with proper type conversions.
 */
@Service
public class WorkExperienceRowMapper implements BiFunction<Row, String, WorkExperience> {

    private final ColumnConverter converter;

    public WorkExperienceRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link WorkExperience} stored in the database.
     */
    @Override
    public WorkExperience apply(Row row, String prefix) {
        WorkExperience entity = WorkExperience.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setEmploymentType(converter.fromRow(row, prefix + "_employment_type", EmploymentType.class));
        entity.setLocation(converter.fromRow(row, prefix + "_location", String.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", Instant.class));
        entity.setEndDate(converter.fromRow(row, prefix + "_end_date", Instant.class));
        entity.setPersonalProfileId(converter.fromRow(row, prefix + "_personal_profile_id", Long.class));
        return entity;
    }
}
