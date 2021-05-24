package com.company.pm.uploadservice.web;

import com.company.pm.common.utils.FileUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.uploadservice.domain.services.CloudinaryService;
import com.company.pm.userservice.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
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
        path = "/bg",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<Map> uploadBgImg(
        @RequestPart("bgImg") Mono<FilePart> file,
        @ApiIgnore ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
            .flatMap(principal -> {
                if (principal instanceof AbstractAuthenticationToken) {
                    return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal)
                        .flatMap(user -> file.ofType(FilePart.class)
                            .flatMap(FileUtils::readBytesContent)
                            .map(bytes -> {
                                try {
                                    return cloudinaryService.uploadBgImg(user.getId(), bytes);
                                } catch (IOException e) {
                                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                                }
                            })
                        );
                } else {
                    return Mono.error(new BadRequestAlertException("Invalid user", "user", "principalinvalid"));
                }
            });
    }
}
