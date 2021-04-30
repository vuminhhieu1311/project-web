package com.company.pm.personalservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.domain.personalservice.Certification;
import com.company.pm.personalservice.web.CertificationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class CertificationRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Certification> {

    public CertificationRepresentationModelAssembler() {
        super(CertificationController.class);
    }

    @Override
    protected String getCollectionName() {
        return "certifications";
    }

    @Override
    protected String getEntityId(EntityModel<Certification> resource) {
        return resource.getContent().getId().toString();
    }

    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        assert attributes != null;

        return linkTo(methodOn(CertificationController.class).getCerts(attributes.get("id"), exchange), exchange);
    }
}
