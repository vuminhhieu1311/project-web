package com.company.pm.interactionservice.web;

import com.company.pm.domain.Attachment;
import com.company.pm.interactionservice.domain.assembler.AttachmentRepresentationModelAssembler;
import com.company.pm.interactionservice.domain.services.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/posts/{id}/attachments",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class AttachmentController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "attachment";
    
    private final AttachmentRepresentationModelAssembler assembler;
    
    private final AttachmentService attachmentService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Attachment>>> getAttachmentsOfPost(
        @PathVariable("id") Long postId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Attachment> attachmentFlux = attachmentService.getAttachmentsOfPost(postId);
        
        return assembler.toCollectionModel(attachmentFlux, exchange);
    }
    
    @GetMapping(path = "/{attachmentId}")
    public Mono<EntityModel<Attachment>> getAttachmentOfPost(
        @PathVariable("id") Long postId,
        @PathVariable("attachmentId") Long attachmentId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return attachmentService.getAttachmentOfPost(postId, attachmentId)
            .flatMap(attachment -> assembler.toModel(attachment, exchange));
    }
}
