package com.company.pm.companyservice.domain.services.mapper;

import com.company.pm.common.enumeration.JobType;
import com.company.pm.companyservice.domain.services.dto.JobDTO;
import com.company.pm.domain.companyservice.Job;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobMapper {
    
    private final ModelMapper mapper;
    
    public Job jobDTOToJob(JobDTO jobDTO) {
        Job job = mapper.map(jobDTO, Job.class);
        job.setJobType(JobType.valueOf(jobDTO.getJobType()));
        job.setActivated(Boolean.valueOf(jobDTO.getActivated()));
        
        return job;
    }
}
