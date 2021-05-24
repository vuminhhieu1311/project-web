package com.company.pm.personalservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.repositories.CertificationRepository;
import com.company.pm.personalservice.domain.repositories.PersonalProfileRepository;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import com.company.pm.personalservice.domain.services.mapper.CertificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class CertificationService {
    
    private static final String ENTITY_NAME = "certification";
    
    private final CertificationMapper mapper;
    
    private final CertificationRepository certificationRepository;
    
    private final PersonalProfileRepository profileRepository;
    
    @Transactional(readOnly = true)
    public Flux<Certification> getCertsByProfile(String userId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMapMany(profile -> certificationRepository.findByPersonalProfile(profile.getId()));
    }
    
    @Transactional(readOnly = true)
    public Mono<Certification> getCertByProfile(String userId, Long certId) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(
                Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> certificationRepository.findByIdAndPersonalProfile(certId, profile.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
            ));
    }
    
    @Transactional
    public Mono<Certification> createCertByProfile(String userId, CertificationDTO certificationDTO) {
        return profileRepository.findByUser(userId)
            .switchIfEmpty(
                Mono.error(new BadRequestAlertException("Entity not found", "personal_profile", "idnotfound")))
            .flatMap(profile -> {
                try {
                    Certification certification = mapper.certDTOToCert(certificationDTO);
                    certification.setPersonalProfile(profile);
                    certification.setPersonalProfileId(profile.getId());
                    
                    return certificationRepository.save(certification);
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Certification> updateCertByProfile(String userId, Long certId, CertificationDTO certificationDTO) {
        return getCertByProfile(userId, certId)
            .flatMap(certification -> {
                try {
                    Certification update = mapper.certDTOToCert(certificationDTO);
                    
                    if (update.getName() != null) {
                        certification.setName(update.getName());
                    }
                    if (update.getIssOrganization() != null) {
                        certification.setIssOrganization(update.getIssOrganization());
                    }
                    if (update.getIssDate() != null) {
                        certification.setIssDate(update.getIssDate());
                    }
                    if (update.getExpDate() != null) {
                        certification.setExpDate(update.getExpDate());
                    }
                    if (update.getCredentialID() != null) {
                        certification.setCredentialID(update.getCredentialID());
                    }
                    if (update.getCredentialURL() != null) {
                        certification.setCredentialURL(update.getCredentialURL());
                    }
                    
                    return certificationRepository.save(certification);
                } catch (ParseException e) {
                    return Mono.error(new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "dateinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deleteCertByProfile(String userId, Long certId) {
        return getCertByProfile(userId, certId)
            .flatMap(certificationRepository::delete);
    }
}
