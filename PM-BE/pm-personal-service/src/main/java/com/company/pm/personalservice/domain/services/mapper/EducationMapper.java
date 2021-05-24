package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.domain.services.dto.EducationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class EducationMapper {
    
    private final ModelMapper mapper;
    
    private static final DateFormat df = new SimpleDateFormat("yyyy");
    
    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }
    
    public Education educationDTOToEducation(EducationDTO educationDTO) throws ParseException {
        Education education = mapper.map(educationDTO, Education.class);
        String startDate = educationDTO.getStartYear();
        String endDate = educationDTO.getEndYear();
        
        if (startDate != null && endDate != null) {
            education.setStartDate(df.parse(startDate).toInstant());
            education.setEndDate(df.parse(endDate).toInstant());
        }
        
        return education;
    }
}
