package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.personalservice.PersonalProfile;
import com.company.pm.personalservice.domain.services.dto.PersonalProfileDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class PersonalProfileMapper {

    private final ModelMapper mapper;
    
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }
    
    public PersonalProfile profileDtoToProfile(PersonalProfileDTO profileDTO) throws ParseException {
        PersonalProfile profile = mapper.map(profileDTO, PersonalProfile.class);
        
        if (profileDTO.getBirthday() != null) {
            profile.setBirthday(df.parse(profileDTO.getBirthday()).toInstant());
        } else {
            profile.setBirthday(Instant.EPOCH);
        }
        
        return profile;
    }
}
