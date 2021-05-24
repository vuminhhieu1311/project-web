package com.company.pm.application.config;

import com.company.pm.chatservice.main.config.ChatServiceConfiguration;
import com.company.pm.common.config.ApplicationProperties;
import com.company.pm.common.config.CommonConfiguration;
import com.company.pm.common.config.WebConfigurer;
import com.company.pm.companyservice.main.config.CompanyServiceConfiguration;
import com.company.pm.personalservice.main.config.PersonalServiceConfiguration;
import com.company.pm.searchservice.main.SearchServiceConfiguration;
import com.company.pm.security.config.SecurityConfiguration;
import com.company.pm.socialservice.main.config.SocialServiceConfiguration;
import com.company.pm.uploadservice.main.config.UploadServiceConfiguration;
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
    UploadServiceConfiguration.class,
    UserServiceConfiguration.class,
    PersonalServiceConfiguration.class,
    SocialServiceConfiguration.class,
    ChatServiceConfiguration.class,
    CompanyServiceConfiguration.class,
    SearchServiceConfiguration.class
})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class AppConfiguration {
}
