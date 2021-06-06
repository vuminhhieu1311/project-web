package com.company.pm.domain.interactionservice;

import com.company.pm.domain.userservice.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("likes")
public class Like implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("created_at")
    private Instant createdAt;

    @JsonIgnoreProperties(value = { "author", "comments", "likes" }, allowSetters = true)
    @Transient
    private Post post;

    @Column("post_id")
    private Long postId;

    @Transient
    private User user;

    @Column("user_id")
    private String userId;
}
