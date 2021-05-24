package com.company.pm.userservice.web;

import com.company.pm.userservice.domain.services.dto.AdminUserDTO;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(
    path = "/api/v1/account",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping
    public Mono<AdminUserDTO> getAccount(@ApiIgnore ServerWebExchange exchange) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
                }
            });
    }
}
