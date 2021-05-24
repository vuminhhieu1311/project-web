package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.domain.assembler.CertificationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.CertificationService;
import com.company.pm.personalservice.domain.services.dto.CertificationDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/profile/certs",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class CertificationController {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "certification";

    private final CertificationRepresentationModelAssembler assembler;

    private final CertificationService certificationService;
    
    private final UserService userService;

    @GetMapping
    public Mono<CollectionModel<EntityModel<Certification>>> getCerts(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Certification> certificationFlux = certificationService.getCertsByProfile(user.getId());
    
                            return assembler.toCollectionModel(certificationFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }

    @GetMapping(path = "/{certId}")
    public Mono<EntityModel<Certification>> getCert(
        @PathVariable("certId") Long certId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> certificationService.getCertByProfile(user.getId(), certId)
                            .flatMap(profile -> assembler.toModel(profile, exchange)));
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Certification>>> createCert(
        @Valid CertificationDTO certificationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> certificationService
                            .createCertByProfile(user.getId(), certificationDTO)
                            .flatMap(cert -> assembler
                                .toModel(cert, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cert.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PatchMapping(
        path = "/{certId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Certification>>> updateCert(
        @PathVariable("certId") Long certId,
        CertificationDTO certificationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> certificationService.updateCertByProfile(user.getId(), certId, certificationDTO)
                            .flatMap(cert -> assembler
                                .toModel(cert, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                                                                                cert.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{certId}")
    public Mono<ResponseEntity<Void>> deleteCert(
        @PathVariable("certId") Long certId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> certificationService.deleteCertByProfile(user.getId(), certId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, certId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
