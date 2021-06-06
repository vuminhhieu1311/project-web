package com.company.pm.interactionservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.Attachment;
import com.company.pm.interactionservice.web.AttachmentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class AttachmentRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Attachment> {
    
    public AttachmentRepresentationModelAssembler() {
        super(AttachmentController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "attachments";
    }
    
    @Override
    protected String getEntityId(EntityModel<Attachment> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(AttachmentController.class).getAttachmentsOfPost(
            Long.parseLong(attributes.get("id")), exchange
        ), exchange);
    }
}
