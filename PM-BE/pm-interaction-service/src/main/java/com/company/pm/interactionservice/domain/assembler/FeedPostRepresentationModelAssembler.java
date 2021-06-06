package com.company.pm.interactionservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.web.FeedController;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class FeedPostRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Post> {
    
    public FeedPostRepresentationModelAssembler() {
        super(FeedController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "posts";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(FeedController.class).getPostsInNewsFeed(exchange), exchange);
    }
}
