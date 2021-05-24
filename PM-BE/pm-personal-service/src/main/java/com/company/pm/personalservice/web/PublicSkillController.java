package com.company.pm.personalservice.web;

import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.domain.assembler.PublicSkillRepresentationModelAssembler;
import com.company.pm.personalservice.domain.services.SkillService;
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
    path = "/api/v1/users/{id}/skills",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicSkillController {
    
    private final PublicSkillRepresentationModelAssembler assembler;
    
    private final SkillService skillService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Skill>>> getPublicSkills(
        @PathVariable("id") String userId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Skill> skillFlux = skillService.getSkillsByProfile(userId);
        
        return assembler.toCollectionModel(skillFlux, exchange);
    }
}
