package com.company.pm.userservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import com.company.pm.userservice.web.PublicUserController;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PublicUserRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<UserDTO> {

    public PublicUserRepresentationModelAssembler() {
        super(PublicUserController.class);
    }

    @Override
    protected String getCollectionName() {
        return "users";
    }

    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(PublicUserController.class).getAllUsers(exchange), exchange);
    }
}
