package com.pkfare.tripscale.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration for HTTP clients and JSON processing
 */
@Slf4j
@Configuration
public class HttpClientConfig {
    
    /**
     * Configure RestTemplate with timeout settings
     */
    @Bean
    public RestTemplate restTemplate() {
        log.info("Configuring HttpComponents RestTemplate with 30s timeouts");
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(30));
        factory.setConnectionRequestTimeout(Duration.ofSeconds(30));
        
        return new RestTemplate(factory);
    }
    
    /**
     * Configure ObjectMapper for JSON processing
     */
    @Bean
    public ObjectMapper objectMapper() {
        log.info("Configuring ObjectMapper with SNAKE_CASE property naming strategy");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }
}