package com.company.pm.chatservice.domain.assembler;

import com.company.pm.chatservice.web.ConversationController;
import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.chatservice.Conversation;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Comparator;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class ConversationRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Conversation> {
    
    public ConversationRepresentationModelAssembler() {
        super(ConversationController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "conversations";
    }
    
    @Override
    protected String getEntityId(EntityModel<Conversation> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected Comparator<Conversation> totalSortFunction() {
        return Comparator.comparing(Conversation::getUpdatedAt).reversed();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(ConversationController.class).getConversations(exchange), exchange);
    }
}
