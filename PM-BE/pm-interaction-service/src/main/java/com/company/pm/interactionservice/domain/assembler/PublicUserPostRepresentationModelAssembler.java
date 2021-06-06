package com.company.pm.interactionservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.web.PublicUserPostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicUserPostRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Post> {
    
    public PublicUserPostRepresentationModelAssembler() {
        super(PublicUserPostController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "posts";
    }
    
    @Override
    protected String getEntityId(EntityModel<Post> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(PublicUserPostController.class).getPublicUserPosts(
            attributes.get("id"), exchange
        ), exchange);
    }
}
