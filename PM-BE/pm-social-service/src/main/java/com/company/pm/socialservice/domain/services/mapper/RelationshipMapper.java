package com.company.pm.socialservice.domain.services.mapper;

import com.company.pm.domain.socialservice.Relationship;
import com.company.pm.socialservice.domain.services.dto.RelationshipDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationshipMapper {
    
    private final ModelMapper mapper;
    
    public Relationship relDTOToRel(RelationshipDTO relationshipDTO) {
        return mapper.map(relationshipDTO, Relationship.class);
    }
}
