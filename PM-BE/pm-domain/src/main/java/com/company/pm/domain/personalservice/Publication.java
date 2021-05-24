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
@Table("publications")
public class Publication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("publisher")
    private String publisher;

    @Column("description")
    private String description;

    @Column("publication_date")
    private Instant publicationDate;

    @Column("url")
    private String url;

    @Transient
    @JsonIgnoreProperties(value = { "publication" }, allowSetters = true)
    @Builder.Default
    private Set<Author> authors = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "workExperience", "education", "certifications", "skills", "projects", "publications" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;

    @Column("personal_profile_id")
    private Long personalProfileId;
}
