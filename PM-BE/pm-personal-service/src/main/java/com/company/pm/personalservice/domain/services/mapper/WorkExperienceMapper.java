package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.common.enumeration.EmploymentType;
import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.domain.services.dto.WorkExperienceDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class WorkExperienceMapper {
    
    private final ModelMapper mapper;
    
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM");
    
    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }
    
    public WorkExperience workExpDTOToWorkExp(WorkExperienceDTO workExperienceDTO) throws ParseException {
        WorkExperience workExperience = mapper.map(workExperienceDTO, WorkExperience.class);
        String startDate = workExperienceDTO.getStartYear() + "-" + workExperienceDTO.getStartMonth();
        
        workExperience.setStartDate(df.parse(startDate).toInstant());
        workExperience.setEmploymentType(EmploymentType.valueOf(workExperienceDTO.getEmploymentType()));
    
        if (workExperienceDTO.getEndYear() != null && workExperienceDTO.getStartYear() != null) {
            String endDate = workExperienceDTO.getEndYear() + "-" + workExperienceDTO.getEndMonth();
            workExperience.setEndDate(df.parse(endDate).toInstant());
        }
        
        return workExperience;
    }
}
