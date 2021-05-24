package com.company.pm.userservice.domain.services.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Relation(collectionRelation = "users")
public class UserDTO {

    private String id;
    
    private String login;
    
    private String firstName;
    
    private String lastName;
    
    private String avatarUrl;
}
