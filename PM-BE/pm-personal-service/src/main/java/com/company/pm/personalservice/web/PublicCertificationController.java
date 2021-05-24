package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.assembler.PublicCertificationRepresentationAssembler;
import com.company.pm.personalservice.domain.services.CertificationService;
import lombok.RequiredArgsConstructor;
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
    path = "/api/v1/users/{id}/certs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicCertificationController {
    
    private final PublicCertificationRepresentationAssembler assembler;
    
    private final CertificationService certificationService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Certification>>> getPublicCerts(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Certification> certificationFlux = certificationService.getCertsByProfile(userId);
        
        return assembler.toCollectionModel(certificationFlux, exchange);
    }
}
