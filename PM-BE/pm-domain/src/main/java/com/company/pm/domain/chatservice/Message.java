package com.company.pm.domain.chatservice;

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
@Table("messages")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("content")
    private String content;

    @Column("created_at")
    private Instant createdAt;

    @Column("deleted_at")
    private Instant deletedAt;

    @JsonIgnoreProperties(value = { "creator", "messages" }, allowSetters = true)
    @Transient
    private Conversation conversation;

    @Column("conversation_id")
    private Long conversationId;

    @Transient
    private User sender;

    @Column("sender_id")
    private String senderId;
}
