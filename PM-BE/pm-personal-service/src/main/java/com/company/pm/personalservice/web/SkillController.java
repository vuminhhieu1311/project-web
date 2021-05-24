package com.company.pm.personalservice.web;

import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.domain.assembler.SkillRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.SkillService;
import com.company.pm.personalservice.domain.services.dto.SkillDTO;
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
    path = "/api/v1/profile/skills",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class SkillController {
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private static final String ENTITY_NAME = "skill";
    
    private final SkillRepresentationModelAssembler assembler;
    
    private final SkillService skillService;
    
    private final UserService userService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Skill>>> getSkills(
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> {
                            Flux<Skill> skillFlux = skillService.getSkillsByProfile(user.getId());
                            
                            return assembler.toCollectionModel(skillFlux, exchange);
                        });
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Skill>>> createSkill(
        @Valid SkillDTO skillDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> skillService.createSkillByProfile(user.getId(), skillDTO)
                            .flatMap(skill -> assembler
                                .toModel(skill, exchange)
                                .map(model -> ResponseEntity
                                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, skill.getId().toString()))
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
        path = "/{skillId}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Skill>>> updateSkill(
        @PathVariable("skillId") Long skillId,
        SkillDTO skillDTO,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> skillService.updateSkillByProfile(user.getId(), skillId, skillDTO)
                            .flatMap(skill -> assembler
                                .toModel(skill, exchange)
                                .map(model -> ResponseEntity
                                    .ok()
                                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skill.getId().toString()))
                                    .body(model)
                                )
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @DeleteMapping(path = "/{skillId}")
    public Mono<ResponseEntity<Void>> deleteSkill(
        @PathVariable("skillId") Long skillId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> skillService.deleteSkillByProfile(user.getId(), skillId)
                            .thenReturn(ResponseEntity.noContent()
                                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, skillId.toString()))
                                .build()
                            )
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
