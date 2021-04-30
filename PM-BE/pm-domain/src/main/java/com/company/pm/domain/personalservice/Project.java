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
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("projects")
public class Project implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

    @Column("url")
    private String url;

    @Transient
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<Creator> creators = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "workExperience", "education", "certifications", "skills", "projects", "publications" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;

    @Column("personal_profile_id")
    private Long personalProfileId;
}
