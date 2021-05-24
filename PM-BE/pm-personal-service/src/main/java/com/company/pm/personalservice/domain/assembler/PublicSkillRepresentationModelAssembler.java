package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Skill;
import com.company.pm.personalservice.web.PublicSkillController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicSkillRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Skill> {
    
    public PublicSkillRepresentationModelAssembler() {
        super(PublicSkillController.class);
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
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(PublicSkillController.class).getPublicSkills(
            attributes.get("id"), exchange
        ), exchange);
    }
}
