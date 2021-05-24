package com.company.pm.companyservice.domain.repositories.rowmapper;

import com.company.pm.common.enumeration.JobType;
import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.companyservice.Job;
import io.r2dbc.spi.Row;

import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Job}, with proper type conversions.
 */
@Service
public class JobRowMapper implements BiFunction<Row, String, Job> {

    private final ColumnConverter converter;

    public JobRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Job} stored in the database.
     */
    @Override
    public Job apply(Row row, String prefix) {
        Job entity = Job.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setLocation(converter.fromRow(row, prefix + "_location", String.class));
        entity.setJobType(converter.fromRow(row, prefix + "_job_type", JobType.class));
        entity.setContactEmail(converter.fromRow(row, prefix + "_contact_email", String.class));
        entity.setCreatedAt(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setClosedAt(converter.fromRow(row, prefix + "_closed_at", Instant.class));
        entity.setActivated(converter.fromRow(row, prefix + "_activated", Boolean.class));
        entity.setCompanyId(converter.fromRow(row, prefix + "_company_id", Long.class));
        entity.setPosterId(converter.fromRow(row, prefix + "_poster_id", String.class));
        return entity;
    }
}
