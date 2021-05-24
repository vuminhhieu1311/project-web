package com.company.pm.companyservice.domain.repositories.rowmapper;

import com.company.pm.common.enumeration.CompanyType;
import com.company.pm.common.services.ColumnConverter;
import com.company.pm.domain.companyservice.Company;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Company}, with proper type conversions.
 */
@Service
public class CompanyRowMapper implements BiFunction<Row, String, Company> {

    private final ColumnConverter converter;

    public CompanyRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Company} stored in the database.
     */
    @Override
    public Company apply(Row row, String prefix) {
        Company entity = Company.builder().build();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setWebsite(converter.fromRow(row, prefix + "_website", String.class));
        entity.setIndustry(converter.fromRow(row, prefix + "_industry", String.class));
        entity.setCompanySize(converter.fromRow(row, prefix + "_company_size", Integer.class));
        entity.setCompanyType(converter.fromRow(row, prefix + "_company_type", CompanyType.class));
        entity.setLogoUrl(converter.fromRow(row, prefix + "_logo_url", String.class));
        entity.setTagline(converter.fromRow(row, prefix + "_tagline", String.class));
        entity.setAdminId(converter.fromRow(row, prefix + "_admin_id", String.class));
        return entity;
    }
}
