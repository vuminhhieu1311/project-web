package com.company.pm.domain.searchservice;

import com.company.pm.common.enumeration.JobType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Document(indexName = "jobs")
public class JobSearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    
    @Field(type = FieldType.Search_As_You_Type)
    private String title;
    
    @Field(type = FieldType.Search_As_You_Type)
    private String company;
    
    @Field(type = FieldType.Search_As_You_Type)
    private String location;
    
    @Field(type = FieldType.Keyword, name = "job_type")
    private JobType jobType;
    
    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd", name = "created_at")
    private Instant createdAt;
}
