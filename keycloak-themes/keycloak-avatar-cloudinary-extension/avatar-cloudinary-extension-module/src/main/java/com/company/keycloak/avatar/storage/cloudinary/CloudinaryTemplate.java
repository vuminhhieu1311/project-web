package com.company.keycloak.avatar.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryTemplate {
    
    private final CloudinaryConfig cloudinaryConfig;
    
    public CloudinaryTemplate(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }
    
    public <T> T execute(CloudinaryCallback<T> callback) {
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret()
            ));
            
            return callback.doInCloudinary(cloudinary);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getFolderName(String realmName) {
        return realmName + cloudinaryConfig.getDefaultResourceSuffix();
    }
}
