package com.company.pm.domain.socialservice;

import java.io.Serial;
import java.io.Serializable;

import com.company.pm.common.enumeration.RelStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("relationship")
public class Relationship implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("requester_id")
    private String requesterId;

    @Column("addressee_id")
    private String addresseeId;

    @Column("status")
    private RelStatus status;

    @Column("performer_id")
    private String performerId;
}
