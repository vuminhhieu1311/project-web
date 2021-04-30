package com.company.pm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
public abstract class AbstractAuditingEntity implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Column("created_by")
    @JsonIgnore
    private String createdBy;
    
    @CreatedDate
    @Column("created_date")
    @JsonIgnore
    private Instant createdDate = Instant.now();
    
    @Column("last_modified_by")
    @JsonIgnore
    private String lastModifiedBy;
    
    @LastModifiedDate
    @Column("last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();
}
