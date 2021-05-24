package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.domain.services.dto.SkillDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillMapper {
    
    private final ModelMapper mapper;
    
    public Skill skillDTOToSkill(SkillDTO skillDTO) {
        Skill skill = mapper.map(skillDTO, Skill.class);
        if (skill.getCategory() == null) {
            skill.setCategory("Other Skills");
        }
        
        return skill;
    }
}
