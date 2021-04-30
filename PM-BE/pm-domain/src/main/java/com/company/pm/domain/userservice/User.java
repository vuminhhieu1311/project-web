package com.company.pm.domain.userservice;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("users")
public class User extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;
    
    @Size(max = 50)
    @Column("first_name")
    private String firstName;
    
    @Size(max = 50)
    @Column("last_name")
    private String lastName;
    
    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    @NotNull
    private boolean activated = false;
    
    @Size(min = 2, max = 10)
    @Column("lang_key")
    private String langKey;
    
    @Size(max = 256)
    @Column("image_url")
    private String imageUrl;
    
    @JsonIgnore
    @Transient
    private Set<Authority> authorities = new HashSet<>();
}
