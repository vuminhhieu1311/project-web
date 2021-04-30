package com.company.pm.domain.userservice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Table("authorities")
public class Authority implements Serializable, Persistable<String> {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @Size(max = 50)
    private String name;
    
    @Override
    public String getId() {
        return name;
    }
    
    @Override
    public boolean isNew() {
        return true;
    }
}
