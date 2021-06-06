package com.company.pm.searchservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.searchservice.UserSearch;
import com.company.pm.searchservice.web.UserSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class UserSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<UserSearch> {
    
    public UserSearchRepresentationModelAssembler() {
        super(UserSearchController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "users";
    }
    
    @Override
    protected String getEntityId(EntityModel<UserSearch> resource) {
        return resource.getContent().getId();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(UserSearchController.class).getUserSearchResult(
            attributes.get("query"), exchange
        ), exchange);
    }
}
