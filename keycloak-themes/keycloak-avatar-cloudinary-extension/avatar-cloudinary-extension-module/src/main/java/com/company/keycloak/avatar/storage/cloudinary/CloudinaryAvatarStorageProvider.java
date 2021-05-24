package com.company.keycloak.avatar.storage.cloudinary;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.company.keycloak.avatar.storage.AvatarStorageProvider;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CloudinaryAvatarStorageProvider implements AvatarStorageProvider {
    
    private final CloudinaryTemplate cloudinaryTemplate;
    
    public CloudinaryAvatarStorageProvider(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryTemplate = new CloudinaryTemplate(cloudinaryConfig);
    }
    
    @Override
    public Map saveAvatarImage(String realmName, String userId, InputStream input) {
        String folder = cloudinaryTemplate.getFolderName(realmName);
        Map params = ObjectUtils.asMap(
            "public_id", folder + "/" + userId + "/" + Instant.now().getEpochSecond(),
            "overwrite", true,
            "format", "png",
            "resource_type", "image",
            "tags", List.of("avatar")
        );
        
        return cloudinaryTemplate.execute(cloudinary ->
            cloudinary.uploader().upload(IOUtils.toByteArray(input), params)
        );
    }
    
    @Override
    public Map loadAvatarImage(String realmName, String userId) {
        String folder = cloudinaryTemplate.getFolderName(realmName);
        
        return cloudinaryTemplate.execute(cloudinary -> {
            ApiResponse response = cloudinary.search()
                .expression("folder:" + folder + "/" + userId + " AND tags=avatar AND resource_type:image")
                .sortBy("uploaded_at", "desc")
                .maxResults(1)
                .execute();
            
            return response;
        });
    }
    
    @Override
    public void close() {
        // NOOP
    }
}
