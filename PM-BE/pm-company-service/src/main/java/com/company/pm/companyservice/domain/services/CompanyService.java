package com.company.pm.companyservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.repositories.CompanyRepository;
import com.company.pm.companyservice.domain.services.dto.CompanyDTO;
import com.company.pm.companyservice.domain.services.mapper.CompanyMapper;
import com.company.pm.domain.companyservice.Company;
import com.company.pm.domain.searchservice.CompanySearch;
import com.company.pm.searchservice.domain.repositories.CompanySearchRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CompanyService {
    
    private static final String ENTITY_NAME = "company";
    
    private final CompanyMapper mapper;
    
    private final CompanyRepository companyRepository;
    
    private final CompanySearchRepository companySearchRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<Company> getPublicCompanies() {
        return companyRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Mono<Company> getPublicCompany(Long companyId) {
        return companyRepository.findById(companyId);
    }
    
    @Transactional(readOnly = true)
    public Flux<Company> getCompaniesByUser(String userId) {
        return companyRepository.findByAdmin(userId);
    }
    
    @Transactional(readOnly = true)
    public Mono<Company> getCompanyByUser(String userId, Long companyId) {
        return companyRepository.findByAdminAndId(userId, companyId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional
    public Mono<Company> createCompanyByUser(String userId, CompanyDTO companyDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> {
                try {
                    Company company = mapper.companyDTOToCompany(companyDTO);
                    company.setAdmin(user);
                    company.setAdminId(user.getId());
    
                    return companyRepository.save(company)
                        .flatMap(saved -> companySearchRepository.save(
                            new CompanySearch(saved.getId(), saved.getName(), saved.getLogoUrl())
                         )
                            .thenReturn(saved)
                        );
                } catch (NumberFormatException e) {
                    return Mono.error(new BadRequestAlertException("Invalid field", ENTITY_NAME, "fieldinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Company> updateCompanyByUser(String userId, Long companyId, CompanyDTO companyDTO) {
        return getCompanyByUser(userId, companyId)
            .flatMap(company -> {
                try {
                    Company update = mapper.companyDTOToCompany(companyDTO);
                    
                    if (update.getName() != null) {
                        company.setName(update.getName());
                    }
                    if (update.getWebsite() != null) {
                        company.setWebsite(update.getWebsite());
                    }
                    if (update.getIndustry() != null) {
                        company.setIndustry(update.getIndustry());
                    }
                    if (update.getCompanySize() != null) {
                        company.setCompanySize(update.getCompanySize());
                    }
                    if (update.getCompanyType() != null) {
                        company.setCompanyType(update.getCompanyType());
                    }
                    if (update.getLogoUrl() != null) {
                        company.setLogoUrl(update.getLogoUrl());
                    }
                    if (update.getTagline() != null) {
                        company.setTagline(update.getTagline());
                    }
                    if (update.getBgImageUrl() != null) {
                        company.setBgImageUrl(update.getBgImageUrl());
                    }
                    
                    return companyRepository.save(company)
                        .flatMap(saved -> companySearchRepository.save(
                            new CompanySearch(saved.getId(), saved.getName(), saved.getLogoUrl())
                         )
                            .thenReturn(saved)
                        );
                } catch (NumberFormatException e) {
                    return Mono.error(new BadRequestAlertException("Invalid field", ENTITY_NAME, "fieldinvalid"));
                }
            });
    }
    
    @Transactional
    public Mono<Void> deleteCompanyByUser(String userId, Long companyId) {
        return getCompanyByUser(userId, companyId)
            .flatMap(companyRepository::delete)
            .then(companySearchRepository.deleteById(companyId));
    }
    
    /**
     * Sync with Elasticsearch
     * <p>
     * This is scheduled to get fired everyday, at 02:00 (am).
     */
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncCompanySearch() {
        syncCompanySearchReactively().blockLast();
    }
    
    @Transactional
    public Flux<CompanySearch> syncCompanySearchReactively() {
        return companySearchRepository.deleteAll()
            .thenMany(companyRepository.findAll()
                .flatMap(company -> companySearchRepository.save(
                    new CompanySearch(company.getId(), company.getName(), company.getLogoUrl()))
                )
            );
    }
}
