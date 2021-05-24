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
@Document(indexName = "users")
public class UserSearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Field(type = FieldType.Search_As_You_Type, name = "first_name")
    private String firstName;
    
    @Field(type = FieldType.Search_As_You_Type, name = "last_name")
    private String lastName;
}
