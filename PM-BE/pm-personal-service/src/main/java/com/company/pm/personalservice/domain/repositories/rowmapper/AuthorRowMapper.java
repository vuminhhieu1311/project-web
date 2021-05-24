package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.Author;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Author}, with proper type conversions.
 */
@Service
public class AuthorRowMapper implements BiFunction<Row, String, Author> {

    private final ColumnConverter converter;

    public AuthorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Author} stored in the database.
     */
    @Override
    public Author apply(Row row, String prefix) {
        Author entity = Author.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setPublicationId(converter.fromRow(row, prefix + "_publication_id", Long.class));
        return entity;
    }
}
