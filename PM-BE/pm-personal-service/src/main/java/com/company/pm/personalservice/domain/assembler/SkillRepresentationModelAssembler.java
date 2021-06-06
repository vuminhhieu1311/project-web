package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.web.SkillController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class SkillRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Skill> {
    
    public SkillRepresentationModelAssembler() {
        super(SkillController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "skills";
    }
    
    @Override
    protected String getEntityId(EntityModel<Skill> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(SkillController.class).getSkills(exchange), exchange);
    }
}
