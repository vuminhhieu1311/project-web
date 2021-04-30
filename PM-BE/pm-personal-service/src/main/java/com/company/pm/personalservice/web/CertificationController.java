package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.assembler.CertificationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.CertificationService;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/users/{id}/profile/certs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CertificationController {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "certification";

    private final CertificationRepresentationModelAssembler assembler;

    private final CertificationService certificationService;

    @GetMapping
    public Mono<CollectionModel<EntityModel<Certification>>> getCerts(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Certification> certificationFlux = certificationService.getCertsByProfile(userId);

        return assembler.toCollectionModel(certificationFlux, exchange);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Certification>>> createCert(
        @PathVariable("id") String userId,
        @Valid CertificationDTO certificationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return certificationService.createCertByProfile(userId, certificationDTO)
            .flatMap(cert ->
                assembler.toModel(cert, exchange)
                    .map(model -> ResponseEntity
                        .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cert.getId().toString()))
                        .body(model)
                    )
            );
    }
}
