package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Project;
import com.company.pm.personalservice.web.PublicProjectController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicProjectRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Project> {
    
    public PublicProjectRepresentationModelAssembler() {
        super(PublicProjectController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "projects";
    }
    
    @Override
    protected String getEntityId(EntityModel<Project> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(PublicProjectController.class).getPublicProjects(
            attributes.get("id"), exchange
        ), exchange);
    }
}
