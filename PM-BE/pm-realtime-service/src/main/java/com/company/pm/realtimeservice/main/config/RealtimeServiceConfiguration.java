package com.company.pm.realtimeservice.main.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
    "com.company.pm.realtimeservice",
    "com.company.pm.chatservice",
    "com.company.pm.interactionservice"
})
@Import({RSocketConfiguration.class})
public class RealtimeServiceConfiguration {
}
