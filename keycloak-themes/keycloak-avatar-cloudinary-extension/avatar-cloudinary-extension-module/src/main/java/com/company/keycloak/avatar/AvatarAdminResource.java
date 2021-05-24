package com.company.keycloak.avatar;

import com.company.keycloak.avatar.storage.AvatarStorageProvider;

import java.io.InputStream;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.keycloak.common.ClientConnection;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.JWSInputException;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.representations.AccessToken;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.admin.AdminAuth;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;
import org.keycloak.services.resources.admin.permissions.AdminPermissions;

@Produces({"application/json"})
public class AvatarAdminResource extends AbstractAvatarResource {
    private static final Logger logger = Logger.getLogger(AvatarAdminResource.class);

    private AdminPermissionEvaluator realmAuth;

    @Context
    private AvatarStorageProvider avatarStorageProvider;

    @Context
    private HttpHeaders httpHeaders;

    @Context
    private ClientConnection clientConnection;

    private AdminAuth auth;

    public AvatarAdminResource(KeycloakSession session) {
        super(session);
    }

    public void init() {
        RealmModel realm = session.getContext().getRealm();

        auth = authenticateRealmAdminRequest();

        RealmManager realmManager = new RealmManager(session);
        if (realm == null) throw new NotFoundException("Realm not found.");

        if (!auth.getRealm().equals(realmManager.getKeycloakAdminstrationRealm())
                && !auth.getRealm().equals(realm)) {
            throw new ForbiddenException();
        }
        realmAuth = AdminPermissions.evaluator(session, realm, auth);

        session.getContext().setRealm(realm);
    }

    @GET
    @Path("/{user_id}")
    public Response downloadUserAvatarImage(@PathParam("user_id") String userId) {
        try {
            canViewUsers();

            return Response.ok(fetchUserImage(session.getContext().getRealm().getName(), userId))
                .build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("error getting user avatar", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @NoCache
    @Path("/{user_id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadUserAvatarImage(@PathParam("user_id") String userId, MultipartFormDataInput input) {
        try {
            if (auth == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            canManageUsers();

            String realmName = session.getContext().getRealm().getName();

            InputStream imageInputStream = input.getFormDataPart(AVATAR_IMAGE_PARAMETER, InputStream.class, null);
            Map response = saveUserImage(realmName, userId, imageInputStream);
    
            return Response.ok(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("error saving user avatar", e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    protected AdminAuth authenticateRealmAdminRequest() {
        String tokenString = AppAuthManager.extractAuthorizationHeaderToken(httpHeaders);
        MultivaluedMap<String, String> queryParameters = session.getContext().getUri().getQueryParameters();
        if (tokenString == null && queryParameters.containsKey("access_token")) {
            tokenString = queryParameters.getFirst("access_token");
        }
        if (tokenString == null) throw new NotAuthorizedException("Bearer");
        AccessToken token;
        try {
            JWSInput input = new JWSInput(tokenString);
            token = input.readJsonContent(AccessToken.class);
        } catch (JWSInputException e) {
            throw new NotAuthorizedException("Bearer token format error");
        }
        String realmName = token.getIssuer().substring(token.getIssuer().lastIndexOf('/') + 1);
        RealmManager realmManager = new RealmManager(session);
        RealmModel realm = realmManager.getRealmByName(realmName);
        if (realm == null) {
            throw new NotAuthorizedException("Unknown realm in token");
        }
        session.getContext().setRealm(realm);
        AppAuthManager.BearerTokenAuthenticator authenticator = new AppAuthManager.BearerTokenAuthenticator(session);
        authenticator.setTokenString(tokenString);
        authenticator.setRealm(realm);
        authenticator.setUriInfo(session.getContext().getUri());
        authenticator.setConnection(clientConnection);
        authenticator.setHeaders(httpHeaders);
        AuthenticationManager.AuthResult authResult = authenticator.authenticate();
        if (authResult == null) {
            logger.debug("Token not valid");
            throw new NotAuthorizedException("Bearer");
        }

        ClientModel client = realm.getClientByClientId(token.getIssuedFor());
        if (client == null) {
            throw new NotFoundException("Could not find client for authorization");

        }

        return new AdminAuth(realm, authResult.getToken(), authResult.getUser(), client);
    }


    private void canViewUsers() {
        if (!realmAuth.users().canView()) {
            logger.info("user does not have permission to view users");
            throw new ForbiddenException("user does not have permission to view users");
        }
    }

    private void canManageUsers() {
        if (!realmAuth.users().canManage()) {
            logger.info("user does not have permission to manage users");
            throw new ForbiddenException("user does not have permission to manage users");
        }
    }
}
