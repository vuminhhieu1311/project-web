package com.company.pm.chatservice.domain.assembler;

import com.company.pm.chatservice.web.MessageController;
import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.chatservice.Message;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Comparator;
import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class MessageRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Message> {
    
    public MessageRepresentationModelAssembler() {
        super(MessageController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "messages";
    }
    
    @Override
    protected String getEntityId(EntityModel<Message> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected Comparator<Message> totalSortFunction() {
        return Comparator.comparing(Message::getCreatedAt).reversed();
    }
    
    @Override
    protected Comparator<Message> chunkSortFunction() {
        return Comparator.comparing(Message::getCreatedAt);
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(MessageController.class).getChatHistory(
            Long.parseLong(attributes.get("id")), exchange
        ), exchange);
    }
}
