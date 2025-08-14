package com.pkfare.tripscale.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.time.Duration;

/**
 * Configuration class for external service clients including HTTP client settings,
 * timeout configurations, and retry policies.
 */
@Slf4j
@Configuration
public class ExternalServiceConfig {
    
    /**
     * Configure RestTemplate with timeout and retry policies for external service calls
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        log.info("Configuring RestTemplate with connect timeout: 10s, read timeout: 30s");
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }
}