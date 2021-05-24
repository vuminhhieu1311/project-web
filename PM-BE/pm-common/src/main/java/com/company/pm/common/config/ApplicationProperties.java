package com.company.pm.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to PMApp.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
public class ApplicationProperties {
    
    private final CldConfig cldConfig = new CldConfig();
    
    @Getter
    @Setter
    public static class CldConfig {
        
        private String cloudName;
        
        private String apiKey;
        
        private String apiSecret;
    }
}
