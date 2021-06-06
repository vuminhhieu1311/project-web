package com.company.pm.domain.companyservice;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.company.pm.common.enumeration.CompanyType;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.domain.interactionservice.Post;
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
@Table("companies")
public class Company implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("website")
    private String website;

    @Column("industry")
    private String industry;

    @Column("company_size")
    private Integer companySize;

    @Column("company_type")
    private CompanyType companyType;

    @Column("logo_url")
    private String logoUrl;

    @Column("tagline")
    private String tagline;
    
    @Column("bg_image_url")
    private String bgImageUrl;
    
    @Transient
    private User admin;
    
    @Column("admin_id")
    private String adminId;
    
    @Transient
    @JsonIgnoreProperties(value = {"company", "author", "comments", "likes" }, allowSetters = true)
    @Builder.Default
    private Set<Post> posts = new HashSet<>();
    
    @Transient
    @JsonIgnoreProperties(value = { "company", "parentComment", "post", "author", "commentLikes" }, allowSetters = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();
}
