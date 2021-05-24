package com.company.pm.chatservice.main.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.company.pm.chatservice"})
@Import({WebSocketConfiguration.class})
public class ChatServiceConfiguration {
}
