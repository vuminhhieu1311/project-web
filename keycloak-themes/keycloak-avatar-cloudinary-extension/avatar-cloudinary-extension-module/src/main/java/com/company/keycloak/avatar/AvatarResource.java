package com.company.keycloak.avatar;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Produces({"application/json"})
public class AvatarResource extends AbstractAvatarResource {
    private static final Logger log = Logger.getLogger(AvatarResource.class);

    public static final String STATE_CHECKER_ATTRIBUTE = "state_checker";
    public static final String STATE_CHECKER_PARAMETER = "stateChecker";

    private final AuthenticationManager.AuthResult auth;

    public AvatarResource(KeycloakSession session) {
        super(session);
        this.auth = resolveAuthentication(session);
    }

    private AuthenticationManager.AuthResult resolveAuthentication(KeycloakSession keycloakSession) {
        AppAuthManager appAuthManager = new AppAuthManager();
        RealmModel realm = keycloakSession.getContext().getRealm();

        AuthenticationManager.AuthResult authResult = appAuthManager.authenticateIdentityCookie(keycloakSession, realm);
        if (authResult != null) {
            return authResult;
        }

        return null;
    }

    @Path("/admin")
    public AvatarAdminResource admin() {
        AvatarAdminResource service = new AvatarAdminResource(session);
        ResteasyProviderFactory.getInstance().injectProperties(service);
        service.init();
        return service;
    }

    @GET
    public Response downloadCurrentUserAvatarImage() {
        if (auth == null) {
            return badRequest();
        }

        String realmName = auth.getSession().getRealm().getName();
        String userId = auth.getUser().getId();
        
        try {
            return Response.ok(fetchUserImage(realmName, userId))
                .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @NoCache
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCurrentUserAvatarImage(MultipartFormDataInput input, @Context UriInfo uriInfo) {
        if (auth == null) {
            return badRequest();
        }

        if (!isValidStateChecker(input)) {
            return badRequest();
        }

        try {
            InputStream imageInputStream = input.getFormDataPart(AVATAR_IMAGE_PARAMETER, InputStream.class, null);

            String realmName = auth.getSession().getRealm().getName();
            String userId = auth.getUser().getId();

            Map response = saveUserImage(realmName, userId, imageInputStream);
            
            return Response.ok(response)
                .build();
        } catch (Exception ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }

    private boolean isValidStateChecker(MultipartFormDataInput input) {
        try {
            String actualStateChecker = input.getFormDataPart(STATE_CHECKER_PARAMETER, String.class, null);
            String requiredStateChecker = (String) session.getAttribute(STATE_CHECKER_ATTRIBUTE);

            return Objects.equals(requiredStateChecker, actualStateChecker);
        } catch (Exception ex) {
            return false;
        }
    }

}
