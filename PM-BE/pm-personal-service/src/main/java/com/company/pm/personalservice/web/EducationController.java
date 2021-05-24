package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Education;
import com.company.pm.personalservice.domain.assembler.EducationRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.EducationService;
import com.company.pm.personalservice.domain.services.dto.EducationDTO;
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
    path = "/api/v1/profile/educations",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class EducationController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "education";
    
    private final EducationRepresentationModelAssembler assembler;
    
    private final EducationService educationService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Education>>> getEdus(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Education> educationFlux = educationService.getEducationsByProfile(user.getId());
    
                            return assembler.toCollectionModel(educationFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @GetMapping(path = "/{eduId}")
    public Mono<EntityModel<Education>> getEdu(
        @PathVariable("eduId") Long eduId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> educationService.getEducationByProfile(user.getId(), eduId)
                            .flatMap(education -> assembler.toModel(education, exchange))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Education>>> createEdu(
        @Valid EducationDTO educationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> educationService.createEducationByProfile(user.getId(), educationDTO)
                            .flatMap(education -> assembler
                                .toModel(education, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, education.getId().toString()))
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
        path = "/{eduId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Education>>> updateEdu(
        @PathVariable("eduId") Long eduId,
        EducationDTO educationDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> educationService.updateEducationByProfile(user.getId(), eduId, educationDTO)
                            .flatMap(education -> assembler
                                .toModel(education, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, education.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{eduId}")
    public Mono<ResponseEntity<Void>> deleteEdu(
        @PathVariable("eduId") Long eduId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> educationService.deleteEducationByProfile(user.getId(), eduId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, eduId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
