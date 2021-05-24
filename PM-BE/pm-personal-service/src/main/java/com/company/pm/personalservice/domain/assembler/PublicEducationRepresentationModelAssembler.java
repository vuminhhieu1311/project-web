package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.web.PublicEducationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicEducationRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Education> {
    
    public PublicEducationRepresentationModelAssembler() {
        super(PublicEducationController.class);
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
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(PublicEducationController.class).getPublicEdus(
            attributes.get("id"), exchange
        ), exchange);
    }
}
