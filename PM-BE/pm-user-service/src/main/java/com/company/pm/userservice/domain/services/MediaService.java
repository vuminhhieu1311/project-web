package com.company.pm.userservice.domain.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaService {
    
    private static final String UPLOAD_FOLDER = "personal-management-collection";
    
    private final Cloudinary cloudinary;
    
    public String getAvatarImgOfUser(String userId) {
        try {
            Map response = cloudinary.search()
                .expression("folder:" + UPLOAD_FOLDER + "/" + userId + " AND tags=avatar AND resource_type:image")
                .sortBy("uploaded_at", "desc")
                .maxResults(1)
                .execute();
    
            String avatarUrl = Arrays.stream(
                response.get("resources").toString().split(","))
                .filter(r -> r.contains("secure_url"))
                .findFirst()
                .get()
                .split("=")[1];
    
            return avatarUrl;
        } catch (Exception e) {
            return null;
        }
    }
}
