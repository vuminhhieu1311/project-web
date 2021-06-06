package com.company.pm.interactionservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.interactionservice.Comment;
import com.company.pm.interactionservice.web.CommentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class CommentRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Comment> {
    
    public CommentRepresentationModelAssembler() {
        super(CommentController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "comments";
    }
    
    @Override
    protected String getEntityId(EntityModel<Comment> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(CommentController.class).getCommentsByPost(
            Long.parseLong(attributes.get("id")), exchange
        ), exchange);
    }
}
