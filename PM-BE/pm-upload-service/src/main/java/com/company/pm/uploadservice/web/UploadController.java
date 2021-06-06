package com.company.pm.uploadservice.web;

import com.company.pm.common.utils.FileUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.uploadservice.domain.services.CloudinaryService;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping(
    path = "/api/v1/upload",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UploadController {
    
    private final CloudinaryService cloudinaryService;
    
    private final UserService userService;
    
    @PostMapping(
        path = "/users/background",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<Map> uploadBgImgOfUser(
        @RequestPart("bgImg") Mono<FilePart> file,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> file.ofType(FilePart.class)
                            .flatMap(FileUtils::readBytesContent)
                            .flatMap(bytes -> cloudinaryService.uploadBgImgOfUser(user.getId(), bytes))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(
        path = "/companies/{companyId}/admin/logo",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<Map> uploadLogoImgOfCompany(
        @PathVariable("companyId") Long companyId,
        @RequestPart("logo") Mono<FilePart> file,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> file.ofType(FilePart.class)
                            .flatMap(FileUtils::readBytesContent)
                            .flatMap(bytes -> cloudinaryService.uploadLogoImgOfCompany(user.getId(), companyId, bytes))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
    
    @PostMapping(
        path = "/companies/{companyId}/admin/background",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<Map> uploadBgImgOfCompany(
        @PathVariable("companyId") Long companyId,
        @RequestPart("bgImg") Mono<FilePart> file,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> file.ofType(FilePart.class)
                            .flatMap(FileUtils::readBytesContent)
                            .flatMap(bytes -> cloudinaryService.uploadBgImgOfCompany(user.getId(), companyId, bytes))
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
