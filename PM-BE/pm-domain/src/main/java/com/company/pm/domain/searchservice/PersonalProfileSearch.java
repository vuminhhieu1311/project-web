package com.company.pm.domain.searchservice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Document(indexName = "personal_profiles")
public class PersonalProfileSearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
    @Field(type = FieldType.Search_As_You_Type)
    private String headline;
    
    @Field(type = FieldType.Search_As_You_Type, name = "first_name")
    private String firstName;
    
    @Field(type = FieldType.Search_As_You_Type, name = "last_name")
    private String lastName;
}
