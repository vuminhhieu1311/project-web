package com.company.pm.domain.personalservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("skills")
public class Skill implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("name")
    private String name;
    
    @Column("category")
    private String category;

    @JsonIgnoreProperties(
        value = { "workExperience", "education", "certifications", "skills", "projects", "publications" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;

    @Column("personal_profile_id")
    private Long personalProfileId;
}
