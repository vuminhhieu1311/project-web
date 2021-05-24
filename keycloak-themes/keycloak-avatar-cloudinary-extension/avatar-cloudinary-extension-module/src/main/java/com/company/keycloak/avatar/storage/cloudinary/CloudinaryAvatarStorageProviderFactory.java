package com.company.keycloak.avatar.storage.cloudinary;

import com.company.keycloak.avatar.storage.AvatarStorageProvider;
import com.company.keycloak.avatar.storage.AvatarStorageProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CloudinaryAvatarStorageProviderFactory implements AvatarStorageProviderFactory {
    
    private static final String DEFAULT_CLOUDINARY_NAME = "htcompany-cloud";
    private static final String DEFAULT_API_KEY = "995711857352625";
    private static final String DEFAULT_API_SECRET = "LdO9RR5qbECftkLMoobFPZFLj8o";
    
    private final CloudinaryConfig cloudinaryConfig;
    
    public CloudinaryAvatarStorageProviderFactory() {
        this.cloudinaryConfig = new CloudinaryConfig(DEFAULT_CLOUDINARY_NAME, DEFAULT_API_KEY, DEFAULT_API_SECRET);
    }
    
    @Override
    public AvatarStorageProvider create(KeycloakSession keycloakSession) {
        return new CloudinaryAvatarStorageProvider(cloudinaryConfig);
    }
    
    @Override
    public void init(Config.Scope scope) {
        // NOOP
    }
    
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // NOOP
    }
    
    @Override
    public void close() {
        // NOOP
    }
    
    @Override
    public String getId() {
        return "avatar-storage-cloudinary";
    }
}
