package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.WorkExperience;
import com.company.pm.personalservice.web.PublicWorkExperienceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicWorkExperienceRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<WorkExperience> {
    
    public PublicWorkExperienceRepresentationModelAssembler() {
        super(PublicWorkExperienceController.class);
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
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(PublicWorkExperienceController.class).getPublicExps(
            attributes.get("id"), exchange
        ), exchange);
    }
}
