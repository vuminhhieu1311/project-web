package com.company.pm.userservice.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
    path = "/api/v1",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class LogoutController {
    
    private final Mono<ClientRegistration> registration;
    
    public LogoutController(ReactiveClientRegistrationRepository registrations) {
        this.registration = registrations.findByRegistrationId("oidc");
    }
    
    @PostMapping(path = "/logout")
    public Mono<Map<String, String>> logout(
        @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken,
        WebSession session
    ) {
        return session
            .invalidate()
            .then(
                this.registration.map(
                    oidc -> oidc.getProviderDetails().getConfigurationMetadata().get("end_session_endpoint").toString())
                    .map(
                        logoutUrl -> {
                            Map<String, String> logoutDetails = new HashMap<>();
                            logoutDetails.put("logoutUrl", logoutUrl);
                            logoutDetails.put("idToken", idToken.getTokenValue());
                            
                            return logoutDetails;
                        }
                    )
            );
    }
}
