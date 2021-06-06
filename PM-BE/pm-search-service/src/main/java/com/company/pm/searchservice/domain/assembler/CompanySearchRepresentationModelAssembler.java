package com.company.pm.searchservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.searchservice.CompanySearch;
import com.company.pm.searchservice.web.CompanySearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class CompanySearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<CompanySearch> {
    
    public CompanySearchRepresentationModelAssembler() {
        super(CompanySearchController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "companies";
    }
    
    @Override
    protected String getEntityId(EntityModel<CompanySearch> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;
        
        return linkTo(methodOn(CompanySearchController.class).getCompanySearchResults(
            attributes.get("query"), exchange
        ), exchange);
    }
}
