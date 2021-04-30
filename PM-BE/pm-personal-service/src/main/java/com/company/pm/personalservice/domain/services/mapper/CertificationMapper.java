package com.company.pm.personalservice.domain.services.mapper;

import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationMapper {

    private final ModelMapper mapper;

    public Certification certDTOToCert(CertificationDTO certificationDTO) {
        return mapper.map(certificationDTO, Certification.class);
    }
}
