package com.company.pm.application.config;

import com.company.pm.common.config.CommonConfiguration;
import com.company.pm.common.config.WebConfigurer;
import com.company.pm.personalservice.main.config.PersonalServiceConfiguration;
import com.company.pm.security.config.SecurityConfiguration;
import com.company.pm.userservice.main.config.UserServiceConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EntityScan(basePackages = {"com.company.pm.domain"})
@Import({
    WebConfigurer.class,
    CommonConfiguration.class,
    SecurityConfiguration.class,
    UserServiceConfiguration.class,
    PersonalServiceConfiguration.class,
})
@EnableConfigurationProperties({LiquibaseProperties.class})
public class AppConfiguration {
}
