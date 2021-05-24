package com.company.pm.domain.chatservice;

import com.company.pm.domain.userservice.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("conversations")
public class Conversation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Transient
    private User creator;

    @Column("creator_id")
    private String creatorId;

    @Transient
    @JsonIgnoreProperties(value = { "conversation", "sender" }, allowSetters = true)
    @Builder.Default
    private Set<Message> messages = new HashSet<>();
    
    @Transient
    @JsonIgnoreProperties(value = { "user", "conversation" }, allowSetters = true)
    @Builder.Default
    private Set<Participant> participants = new HashSet<>();
}
