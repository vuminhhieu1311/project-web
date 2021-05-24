package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.web.WorkExperienceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class WorkExperienceRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<WorkExperience> {
    
    public WorkExperienceRepresentationModelAssembler() {
        super(WorkExperienceController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "work_experiences";
    }
    
    @Override
    protected String getEntityId(EntityModel<WorkExperience> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(WorkExperienceController.class).getExps(exchange), exchange);
    }
}
