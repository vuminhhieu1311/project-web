package com.company.pm.domain.personalservice;

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
@Table("educations")
public class Education implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("school")
    private String school;

    @Column("degree")
    private String degree;

    @Column("field_of_study")
    private String fieldOfStudy;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

    @Column("grade")
    private String grade;

    @Column("activities")
    private String activities;

    @Column("description")
    private String description;
    
    @JsonIgnoreProperties(
        value = { "certifications", "skills", "projects", "publications", "workExperiences", "educations" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;
    
    @Column("personal_profile_id")
    private Long personalProfileId;
}
