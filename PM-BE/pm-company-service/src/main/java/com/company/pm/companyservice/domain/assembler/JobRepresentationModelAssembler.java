package com.company.pm.companyservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.companyservice.web.JobController;
import com.company.pm.domain.companyservice.Job;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class JobRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Job> {
    
    public JobRepresentationModelAssembler() {
        super(JobController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "jobs";
    }
    
    @Override
    protected String getEntityId(EntityModel<Job> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(JobController.class).getJobs(exchange), exchange);
    }
}
