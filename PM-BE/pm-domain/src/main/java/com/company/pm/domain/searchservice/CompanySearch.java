package com.company.pm.domain.searchservice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Document(indexName = "companies")
public class CompanySearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
    @Field(type = FieldType.Search_As_You_Type)
    private String name;
}
