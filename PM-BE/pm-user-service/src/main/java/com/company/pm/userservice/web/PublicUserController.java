package com.company.pm.userservice.web;

import com.company.pm.userservice.domain.assembler.PublicUserRepresentationModelAssembler;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
    path = "/api/v1",
    produces = MediaTypes.HAL_JSON_VALUE
)
@RequiredArgsConstructor
public class PublicUserController {

    private final PublicUserRepresentationModelAssembler assembler;

    private final UserService userService;

    @GetMapping(path = "/users")
    public Mono<CollectionModel<EntityModel<UserDTO>>> getAllUsers(@ApiIgnore ServerWebExchange exchange) {
        Flux<UserDTO> userDtoFlux = userService.getAllPublicUsers();

        return assembler.toCollectionModel(userDtoFlux, exchange);
    }

    @GetMapping("/authorities")
    public Mono<List<String>> getAuthorities() {
        return userService.getAuthorities().collect(Collectors.toList());
    }
}
