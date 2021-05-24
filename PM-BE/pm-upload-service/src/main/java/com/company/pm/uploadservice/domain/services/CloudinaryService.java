package com.company.pm.uploadservice.domain.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    
    private static final String UPLOAD_FOLDER = "personal-management-collection";
    
    private final Cloudinary cloudinary;
    
    public Map uploadBgImg(String userId, byte[] bytes) throws IOException {
        return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
            "public_id", UPLOAD_FOLDER + "/" + userId + "/" + Instant.now().getEpochSecond(),
            "overwrite", true,
            "format", "png",
            "resource_type", "image",
            "tags", List.of("background")
        ));
    }
}
