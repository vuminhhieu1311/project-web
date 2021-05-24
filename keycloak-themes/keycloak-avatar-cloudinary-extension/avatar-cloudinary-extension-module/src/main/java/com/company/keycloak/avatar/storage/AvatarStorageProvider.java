package com.company.keycloak.avatar.storage;

import org.keycloak.provider.Provider;

import java.io.InputStream;
import java.util.Map;

public interface AvatarStorageProvider extends Provider {

    Map saveAvatarImage(String realmName, String userId, InputStream input);

    Map loadAvatarImage(String realmName, String userId);
}
