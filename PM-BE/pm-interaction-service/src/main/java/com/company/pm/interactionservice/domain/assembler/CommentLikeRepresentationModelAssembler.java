package com.company.pm.interactionservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.interactionservice.CommentLike;
import com.company.pm.interactionservice.web.CommentLikeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class CommentLikeRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<CommentLike> {
    
    public CommentLikeRepresentationModelAssembler() {
        super(CommentLikeController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "comment_likes";
    }
    
    @Override
    protected String getEntityId(EntityModel<CommentLike> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(CommentLikeController.class).getCommentLikes(
            Long.parseLong(attributes.get("id")),
            Long.parseLong(attributes.get("commentId")),
            exchange
        ), exchange);
    }
}
