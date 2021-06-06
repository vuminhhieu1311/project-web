package com.company.pm.domain;

import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.domain.interactionservice.Post;
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
@Table("attachments")
public class Attachment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("thumb_url")
    private String thumbUrl;

    @Column("file_url")
    private String fileUrl;

    @Column("created_at")
    private Instant createdAt;

    @JsonIgnoreProperties(value = { "company", "author", "attachments", "comments", "likes" }, allowSetters = true)
    @Transient
    private Post post;

    @Column("post_id")
    private Long postId;

    @JsonIgnoreProperties(value = { "company", "parentComment", "post", "author", "attachments", "commentLikes" }, allowSetters = true)
    @Transient
    private Comment comment;

    @Column("comment_id")
    private Long commentId;
}
