package com.company.keycloak.avatar.storage.cloudinary;

import lombok.Data;

@Data
public class CloudinaryConfig {
    
    private final String cloudName;
    
    private final String apiKey;
    
    private final String apiSecret;
    
    private String defaultResourceSuffix = "-collection";
}
