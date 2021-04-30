package com.company.pm.userservice.web;

import com.company.pm.common.AuthoritiesConstants;
import com.company.pm.userservice.domain.assembler.UserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.AdminUserDTO;
import com.company.pm.userservice.domain.services.mapper.UserMapper;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/admin/v1/users",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    private final UserRepresentationModelAssembler assembler;

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<CollectionModel<EntityModel<AdminUserDTO>>> getAllUsers(@ApiIgnore ServerWebExchange exchange) {
        Flux<AdminUserDTO> userDtoFlux = userService.getAllManagedUsers();

        return assembler.toCollectionModel(userDtoFlux, exchange);
    }

    @GetMapping(path = "/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<EntityModel<AdminUserDTO>> getUser(
        @PathVariable("login") String login,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return userService.getUserWithAuthoritiesByLogin(login)
            .map(userMapper::userToAdminUserDto)
            .flatMap(adminUserDto -> assembler.toModel(adminUserDto, exchange))
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
