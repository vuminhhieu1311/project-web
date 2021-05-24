package com.company.pm.domain.companyservice;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import com.company.pm.common.enumeration.JobType;
import com.company.pm.domain.userservice.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("jobs")
public class Job implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("location")
    private String location;

    @Column("job_type")
    private JobType jobType;

    @Column("contact_email")
    private String contactEmail;
    
    @Column("created_at")
    private Instant createdAt;
    
    @Column("closed_at")
    private Instant closedAt;
    
    @Column("activated")
    private Boolean activated;
    
    @JsonIgnoreProperties(value = { "admin" }, allowSetters = true)
    @Transient
    private Company company;
    
    @Column("company_id")
    private Long companyId;
    
    @Transient
    private User poster;
    
    @Column("poster_id")
    private String posterId;
    
}
