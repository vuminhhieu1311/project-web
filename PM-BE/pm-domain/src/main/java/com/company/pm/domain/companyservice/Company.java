package com.company.pm.domain.companyservice;

import java.io.Serial;
import java.io.Serializable;

import com.company.pm.common.enumeration.CompanyType;
import com.company.pm.domain.userservice.User;
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
    
    @Transient
    private User admin;
    
    @Column("admin_id")
    private String adminId;
}
