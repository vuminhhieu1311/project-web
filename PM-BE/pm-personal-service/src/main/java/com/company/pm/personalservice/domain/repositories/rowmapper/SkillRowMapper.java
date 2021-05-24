package com.company.pm.personalservice.domain.repositories.rowmapper;

import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.personalservice.Skill;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Skill}, with proper type conversions.
 */
@Service
public class SkillRowMapper implements BiFunction<Row, String, Skill> {

    private final ColumnConverter converter;

    public SkillRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Skill} stored in the database.
     */
    @Override
    public Skill apply(Row row, String prefix) {
        Skill entity = Skill.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setPersonalProfileId(converter.fromRow(row, prefix + "_personal_profile_id", Long.class));
        return entity;
    }
}
