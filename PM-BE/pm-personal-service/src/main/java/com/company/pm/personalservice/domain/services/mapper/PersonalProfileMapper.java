package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PersonalProfileMapper {

    private final ModelMapper mapper;

    public PersonalProfile profileDtoToProfile(PersonalProfileDTO profileDTO) {
        PersonalProfile profile = mapper.map(profileDTO, PersonalProfile.class);
        profile.setBirthday(Instant.parse(profileDTO.getBirthday()));
        
        return profile;
    }
}
