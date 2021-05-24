package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.web.EducationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class EducationRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Education> {
    
    public EducationRepresentationModelAssembler() {
        super(EducationController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "educations";
    }
    
    @Override
    protected String getEntityId(EntityModel<Education> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(EducationController.class).getEdus(exchange), exchange);
    }
}
