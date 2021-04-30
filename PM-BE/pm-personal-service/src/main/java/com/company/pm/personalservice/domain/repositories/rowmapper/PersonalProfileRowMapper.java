package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.PersonalProfile;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.BiFunction;

/**
 * Converter between {@link Row} to {@link PersonalProfile}, with proper type conversions.
 */
@Service
public class PersonalProfileRowMapper implements BiFunction<Row, String, PersonalProfile> {

    private final ColumnConverter converter;

    public PersonalProfileRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PersonalProfile} stored in the database.
     */
    @Override
    public PersonalProfile apply(Row row, String prefix) {
        PersonalProfile entity = PersonalProfile.builder().build();
        
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setBirthday(converter.fromRow(row, prefix + "_birthday", Instant.class));
        entity.setCountry(converter.fromRow(row, prefix + "_country", String.class));
        entity.setLocation(converter.fromRow(row, prefix + "_location", String.class));
        entity.setPhoneNumber(converter.fromRow(row, prefix + "_phone_number", String.class));
        entity.setAddress(converter.fromRow(row, prefix + "_address", String.class));
        entity.setAbout(converter.fromRow(row, prefix + "_about", String.class));
        entity.setWorkExperienceId(converter.fromRow(row, prefix + "_work_experience_id", Long.class));
        entity.setEducationId(converter.fromRow(row, prefix + "_education_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        
        return entity;
    }
}
