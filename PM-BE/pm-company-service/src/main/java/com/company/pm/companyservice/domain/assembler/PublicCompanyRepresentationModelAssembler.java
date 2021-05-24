package com.company.pm.companyservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.companyservice.web.PublicCompanyController;
import com.company.pm.domain.companyservice.Company;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicCompanyRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Company> {
    
    public PublicCompanyRepresentationModelAssembler() {
        super(PublicCompanyController.class);
    }
    
    @Override
    protected String getCollectionName() {
        return "companies";
    }
    
    @Override
    protected String getEntityId(EntityModel<Company> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(PublicCompanyController.class).getPublicCompanies(exchange), exchange);
    }
}
