package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Publication;
import com.company.pm.personalservice.web.PublicationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicationRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Publication> {
    
    public PublicationRepresentationModelAssembler() {
        super(PublicationController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "publications";
    }
    
    @Override
    protected String getEntityId(EntityModel<Publication> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(PublicationController.class).getPubs(exchange), exchange);
    }
}
