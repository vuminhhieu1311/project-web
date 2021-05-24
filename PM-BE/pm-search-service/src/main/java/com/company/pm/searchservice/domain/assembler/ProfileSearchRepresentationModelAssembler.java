package com.company.pm.searchservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.searchservice.PersonalProfileSearch;
import com.company.pm.searchservice.web.ProfileSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class ProfileSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<PersonalProfileSearch> {
    
    public ProfileSearchRepresentationModelAssembler() {
        super(ProfileSearchController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "personal_profiles";
    }
    
    @Override
    protected String getEntityId(EntityModel<PersonalProfileSearch> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(ProfileSearchController.class).getSearchResults(
            attributes.get("query"), exchange
        ), exchange);
    }
}
