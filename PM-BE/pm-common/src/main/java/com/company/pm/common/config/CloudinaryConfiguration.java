package com.company.pm.common.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {
    
    @Bean
    public Cloudinary cloudinary(ApplicationProperties properties) {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", properties.getCldConfig().getCloudName(),
            "api_key", properties.getCldConfig().getApiKey(),
            "api_secret", properties.getCldConfig().getApiSecret()
        ));
    }
}
