package com.company.pm.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.support.WebStack;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
@EnableHypermediaSupport(
    type = EnableHypermediaSupport.HypermediaType.HAL,
    stacks = {WebStack.WEBFLUX}
)
@Import({
    DatabaseConfiguration.class,
    AsyncConfiguration.class,
    LiquibaseConfiguration.class,
    LocaleConfiguration.class,
    DateTimeFormatConfiguration.class,
    LoggingConfiguration.class,
    ReactorConfiguration.class,
    OpenApiConfiguration.class,
    ElasticsearchConfiguration.class,
    CloudinaryConfiguration.class
})
public class CommonConfiguration {
    
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ProblemModule());
        mapper.registerModule(new ConstraintViolationProblemModule());
        
        return mapper;
    }
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        return modelMapper;
    }
}
