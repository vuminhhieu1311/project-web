package com.company.pm.domain.personalservice;

import com.company.pm.common.enumeration.EmploymentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("work_experiences")
public class WorkExperience implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("employment_type")
    private EmploymentType employmentType;

    @Column("company")
    private String company;
    
    @Column("location")
    private String location;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;
    
    @JsonIgnoreProperties(
        value = { "certifications", "skills", "projects", "publications", "workExperiences", "educations" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;
    
    @Column("personal_profile_id")
    private Long personalProfileId;
}
