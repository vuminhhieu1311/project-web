package com.company.pm.companyservice.domain.services;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.repositories.CompanyRepository;
import com.company.pm.companyservice.domain.repositories.JobRepository;
import com.company.pm.companyservice.domain.services.dto.JobDTO;
import com.company.pm.companyservice.domain.services.mapper.JobMapper;
import com.company.pm.domain.companyservice.Job;
import com.company.pm.domain.searchservice.JobSearch;
import com.company.pm.searchservice.domain.repositories.JobSearchRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JobService {
    
    private static final String ENTITY_NAME = "job";
    
    private final JobMapper mapper;
    
    private final JobRepository jobRepository;
    
    private final JobSearchRepository jobSearchRepository;
    
    private final CompanyRepository companyRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Flux<Job> getPublicJobs() {
        return jobRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Mono<Job> getPublicJob(Long jobId) {
        return jobRepository.findById(jobId);
    }
    
    @Transactional(readOnly = true)
    public Flux<Job> getJobsByUser(String userId) {
        return jobRepository.findByPoster(userId);
    }
    
    @Transactional(readOnly = true)
    public Mono<Job> getJobByUser(String userId, Long jobId) {
        return jobRepository.findByPosterAndId(userId, jobId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional
    public Mono<Job> createJobByUser(String userId, JobDTO jobDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> companyRepository.findByAdminAndName(userId, jobDTO.getCompany())
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "company", "idnotfound")))
                .flatMap(company -> {
                    Job job = mapper.jobDTOToJob(jobDTO);
                    job.setCompany(company);
                    job.setCompanyId(company.getId());
                    job.setPoster(user);
                    job.setPosterId(user.getId());
                    job.setActivated(true);
                    job.setCreatedAt(Instant.now());
                    job.setClosedAt(Instant.EPOCH);
                    
                    return jobRepository.save(job)
                        .flatMap(saved -> saveJobSearch(saved).thenReturn(saved));
                })
            );
    }
    
    @Transactional
    public Mono<Job> updateJobByUser(String userId, Long jobId, JobDTO jobDTO) {
        return getJobByUser(userId, jobId)
            .flatMap(job -> {
                Job update = mapper.jobDTOToJob(jobDTO);
                
                if (update.getTitle() != null) {
                    job.setTitle(update.getTitle());
                }
                if (update.getLocation() != null) {
                    job.setLocation(update.getLocation());
                }
                if (update.getJobType() != null) {
                    job.setJobType(update.getJobType());
                }
                if (update.getContactEmail() != null) {
                    job.setContactEmail(update.getContactEmail());
                }
                if (update.getActivated() != null) {
                    if (!update.getActivated() && job.getActivated()) {
                        job.setActivated(false);
                        job.setClosedAt(Instant.now());
                    } else if (update.getActivated() && !job.getActivated()) {
                        job.setActivated(true);
                        job.setCreatedAt(Instant.now());
                    } else {
                        return Mono.error(new BadRequestAlertException("Invalid action", ENTITY_NAME, "actioninvalid"));
                    }
                }
                
                return jobRepository.save(job)
                    .flatMap(saved -> companyRepository.findByAdminAndId(userId, saved.getCompanyId())
                        .flatMap(company -> {
                            saved.setCompany(company);
                            
                            return jobSearchRepository.save(jobToJobSearch(saved))
                                .thenReturn(saved);
                        })
                    );
            });
    }
    
    @Transactional
    public Mono<Void> deleteJobByUser(String userId, Long jobId) {
        return getJobByUser(userId, jobId)
            .flatMap(jobRepository::delete)
            .then(jobSearchRepository.deleteById(jobId));
    }
    
    /**
     * Sync with Elasticsearch
     * <p>
     * This is scheduled to get fired everyday, at 02:00 (am).
     */
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncJobSearch() {
        syncJobSearchReactively().blockLast();
    }
    
    @Transactional
    public Flux<JobSearch> syncJobSearchReactively() {
        return jobSearchRepository.deleteAll()
            .thenMany(jobRepository.findAll()
                .flatMap(this::saveJobSearch)
            );
    }
    
    @Transactional
    public Mono<JobSearch> saveJobSearch(Job job) {
        return jobSearchRepository.save(jobToJobSearch(job));
    }
    
    private JobSearch jobToJobSearch(Job job) {
        return new JobSearch(
            job.getId(), job.getTitle(), job.getCompany().getName(),
            job.getLocation(), job.getJobType(), job.getCreatedAt()
        );
    }
}
