package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.common.config.Constants;
import com.company.pm.domain.personalservice.Creator;
import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.domain.services.dto.ProjectDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMapper {
    
    private final ModelMapper mapper;
    
    private final static DateFormat df = new SimpleDateFormat("yyyy-MM");
    
    static {
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE));
    }
    
    public Project projectDTOToProject(ProjectDTO projectDTO) throws ParseException {
        Project project = mapper.map(projectDTO, Project.class);
        String startDate = projectDTO.getStartYear() + "-" + projectDTO.getStartMonth();
        
        if (projectDTO.getCreators() != null && !projectDTO.getCreators().isBlank()) {
            Set<Creator> creators = Arrays.stream(projectDTO.getCreators().split(","))
                .map(name -> Creator.builder().name(name).build())
                .collect(Collectors.toSet());
            project.setCreators(creators);
        }
        
        project.setStartDate(df.parse(startDate).toInstant());
        
        if (projectDTO.getEndYear() != null && projectDTO.getEndMonth() != null) {
            String endDate = projectDTO.getEndYear() + "-" + projectDTO.getEndMonth();
            project.setEndDate(df.parse(endDate).toInstant());
        }
        
        return project;
    }
}
