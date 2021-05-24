package com.company.pm.companyservice.web;

import com.company.pm.companyservice.domain.assembler.PublicCompanyRepresentationModelAssembler;
import com.company.pm.companyservice.domain.services.CompanyService;
import com.company.pm.domain.companyservice.Company;
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
    path = "/api/v1/public/companies",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicCompanyController {

    private final PublicCompanyRepresentationModelAssembler assembler;
    
    private final CompanyService companyService;
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Company>>> getPublicCompanies(
        @ApiIgnore ServerWebExchange exchange
    ) {
        Flux<Company> companyFlux = companyService.getPublicCompanies();
        
        return assembler.toCollectionModel(companyFlux, exchange);
    }
    
    @GetMapping(path = "/{companyId}")
    public Mono<EntityModel<Company>> getPublicCompany(
        @PathVariable("companyId") Long companyId,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return companyService.getPublicCompany(companyId)
            .flatMap(company -> assembler.toModel(company, exchange));
    }
}
