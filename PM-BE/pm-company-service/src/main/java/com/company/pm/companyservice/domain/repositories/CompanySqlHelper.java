package com.company.pm.companyservice.domain.repositories;

import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

import java.util.ArrayList;
import java.util.List;

public class CompanySqlHelper {
    
    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("website", table, columnPrefix + "_website"));
        columns.add(Column.aliased("industry", table, columnPrefix + "_industry"));
        columns.add(Column.aliased("company_size", table, columnPrefix + "_company_size"));
        columns.add(Column.aliased("company_type", table, columnPrefix + "_company_type"));
        columns.add(Column.aliased("logo_url", table, columnPrefix + "_logo_url"));
        columns.add(Column.aliased("tagline", table, columnPrefix + "_tagline"));
        columns.add(Column.aliased("bg_image_url", table, columnPrefix + "_bg_image_url"));
        
        columns.add(Column.aliased("admin_id", table, columnPrefix + "_admin_id"));
        return columns;
    }
}
