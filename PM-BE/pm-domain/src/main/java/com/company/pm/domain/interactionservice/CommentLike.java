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
@Table("comment_likes")
public class CommentLike implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("created_at")
    private Instant createdAt;

    @Transient
    private User user;

    @Column("user_id")
    private String userId;

    @JsonIgnoreProperties(value = { "parentComment", "post", "author", "commentLikes" }, allowSetters = true)
    @Transient
    private Comment comment;

    @Column("comment_id")
    private Long commentId;
}
