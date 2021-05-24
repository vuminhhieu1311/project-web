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
@Table("participants")
public class Participant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    private String userId;

    @Transient
    private User user;

    @JsonIgnoreProperties(value = { "creator", "messages", "participants" }, allowSetters = true)
    @Transient
    private Conversation conversation;

    @Column("conversation_id")
    private Long conversationId;
}
