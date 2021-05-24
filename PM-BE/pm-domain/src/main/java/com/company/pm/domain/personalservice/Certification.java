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
@Table("certifications")
public class Certification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("name")
    private String name;
    
    @Column("iss_organization")
    private String issOrganization;
    
    @Column("iss_date")
    private Instant issDate;
    
    @Column("exp_date")
    private Instant expDate;
    
    @Column("credential_id")
    private String credentialID;
    
    @Column("credential_url")
    private String credentialURL;

    @JsonIgnoreProperties(
        value = { "workExperience", "education", "certifications", "skills", "projects", "publications" },
        allowSetters = true
    )
    @Transient
    private PersonalProfile personalProfile;

    @Column("personal_profile_id")
    private Long personalProfileId;
}
