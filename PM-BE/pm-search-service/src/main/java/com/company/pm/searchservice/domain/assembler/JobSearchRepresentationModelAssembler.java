package com.company.pm.searchservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.searchservice.JobSearch;
import com.company.pm.searchservice.web.JobSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class JobSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<JobSearch> {
    
    public JobSearchRepresentationModelAssembler() {
        super(JobSearchController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "jobs";
    }
    
    @Override
    protected String getEntityId(EntityModel<JobSearch> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(JobSearchController.class).getJobSearchResults(exchange), exchange);
    }
}
