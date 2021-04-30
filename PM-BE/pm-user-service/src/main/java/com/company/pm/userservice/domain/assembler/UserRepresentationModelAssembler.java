package com.company.pm.userservice.domain.assembler;

import com.company.pm.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.AdminUserDTO;
import com.company.pm.userservice.web.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class UserRepresentationModelAssembler
        extends SimpleIdentifiableReactiveRepresentationModelAssembler<AdminUserDTO> {

    public UserRepresentationModelAssembler() {
        super(UserController.class);
    }

    @Override
    protected String getCollectionName() {
        return "users";
    }

    @Override
    protected String getEntityId(EntityModel<AdminUserDTO> resource) {
        return resource.getContent().getId();
    }

    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(UserController.class).getAllUsers(exchange), exchange);
    }
}
