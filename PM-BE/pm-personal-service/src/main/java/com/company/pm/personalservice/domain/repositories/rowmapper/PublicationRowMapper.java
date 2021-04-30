package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.Publication;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link Publication}, with proper type conversions.
 */
@Service
public class PublicationRowMapper implements BiFunction<Row, String, Publication> {

    private final ColumnConverter converter;

    public PublicationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Publication} stored in the database.
     */
    @Override
    public Publication apply(Row row, String prefix) {
        Publication entity = Publication.builder().build();
        
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setPublisher(converter.fromRow(row, prefix + "_publisher", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setPublicationDate(converter.fromRow(row, prefix + "_publication_date", Instant.class));
        entity.setUrl(converter.fromRow(row, prefix + "_url", String.class));
        entity.setPersonalProfileId(converter.fromRow(row, prefix + "_personal_profile_id", Long.class));
        
        return entity;
    }
}
