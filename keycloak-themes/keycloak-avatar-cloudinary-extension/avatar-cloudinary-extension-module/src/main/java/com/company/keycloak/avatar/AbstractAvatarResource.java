package com.company.keycloak.avatar;

import com.company.keycloak.avatar.storage.AvatarStorageProvider;
import com.company.keycloak.avatar.storage.cloudinary.CloudinaryAvatarStorageProviderFactory;

import java.io.InputStream;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.keycloak.models.KeycloakSession;

public abstract class AbstractAvatarResource {
    protected static final String AVATAR_IMAGE_PARAMETER = "image";

    protected KeycloakSession session;

    public AbstractAvatarResource(KeycloakSession session) {
        this.session = session;
    }

    public AvatarStorageProvider getAvatarStorageProvider() {
        return lookupAvatarStorageProvider(session);
    }

    protected AvatarStorageProvider lookupAvatarStorageProvider(KeycloakSession keycloakSession) {
        return new CloudinaryAvatarStorageProviderFactory().create(keycloakSession);
    }

    protected Response badRequest() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    protected Map saveUserImage(String realmName, String userId, InputStream imageInputStream) {
        return getAvatarStorageProvider().saveAvatarImage(realmName, userId, imageInputStream);
    }

    protected Map fetchUserImage(String realmId, String userId) {
        return getAvatarStorageProvider().loadAvatarImage(realmId, userId);
    }
}
