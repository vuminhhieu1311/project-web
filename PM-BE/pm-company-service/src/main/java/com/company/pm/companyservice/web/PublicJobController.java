package com.company.pm.companyservice.web;

import com.company.pm.companyservice.domain.assembler.PublicJobRepresentationModelAssembler;
import com.company.pm.companyservice.domain.services.JobService;
import com.company.pm.domain.companyservice.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/public/jobs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicJobController {

    private final PublicJobRepresentationModelAssembler assembler;
    
    private final JobService jobService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Job>>> getPublicJobs(
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Job> jobFlux = jobService.getPublicJobs();
        
        return assembler.toCollectionModel(jobFlux, exchange);
    }
    
    @GetMapping(path = "/{jobId}")
    public Mono<EntityModel<Job>> getPublicJob(
        @PathVariable("jobId") Long jobId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return jobService.getPublicJob(jobId)
            .flatMap(job -> assembler.toModel(job, exchange));
    }
}
