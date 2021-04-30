package com.company.pm.domain.personalservice;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.userservice.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("personal_profiles")
public class PersonalProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("birthday")
    private Instant birthday;

    @Column("country")
    private String country;

    @Column("location")
    private String location;

    @Pattern(regexp = Constants.PHONE_REGEX)
    @Column("phone_number")
    private String phoneNumber;

    @Column("address")
    private String address;

    @Column("about")
    private String about;

    private Long workExperienceId;

    @Transient
    private WorkExperience workExperience;

    private Long educationId;

    @Transient
    private Education education;

    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    private Set<Certification> certifications = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "creators", "personalProfile" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "authors", "personalProfile" }, allowSetters = true)
    private Set<Publication> publications = new HashSet<>();
    
    private String userId;
    
    @Transient
    private User user;
}
