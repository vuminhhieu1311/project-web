package com.company.pm.personalservice.domain.services;

import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.repositories.CertificationRepository;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import com.company.pm.personalservice.domain.services.mapper.CertificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private static final String ENTITY_NAME = "certification";

    private final CertificationMapper mapper;

    private final CertificationRepository certificationRepository;

    private final PersonalProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public Flux<Certification> getCertsByProfile(String userId) {
        return certificationRepository.findByPersonalProfile(Long.parseLong(userId));
    }

    @Transactional
    public Mono<Certification> createCertByProfile(String userId, CertificationDTO certificationDTO) {
        Certification certification = mapper.certDTOToCert(certificationDTO);

        return profileRepository.findByUser(userId).flatMap(profile -> {
            certification.setPersonalProfile(profile);
            certification.setPersonalProfileId(profile.getId());

            return certificationRepository.save(certification);
        });
    }
}
