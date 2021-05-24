package com.company.pm.domain.personalservice;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.userservice.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @Column("headline")
    private String headline;
    
    @Column("industry")
    private String industry;
    
    @Column("bg_image_url")
    private String bgImageUrl;
    
    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    @JsonIgnore
    @Builder.Default
    private Set<WorkExperience> workExperiences = new HashSet<>();
    
    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    @Builder.Default
    private Set<Education> educations = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    @Builder.Default
    private Set<Certification> certifications = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "personalProfile" }, allowSetters = true)
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "creators", "personalProfile" }, allowSetters = true)
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "authors", "personalProfile" }, allowSetters = true)
    @Builder.Default
    private Set<Publication> publications = new HashSet<>();
    
    @Column("user_id")
    private String userId;
    
    @Transient
    private User user;
}
