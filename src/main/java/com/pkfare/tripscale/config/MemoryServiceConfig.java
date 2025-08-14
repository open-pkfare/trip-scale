package com.pkfare.tripscale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Memory storage service integration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "external.memory")
public class MemoryServiceConfig {
    
    private String baseUrl;
    private String apiKey;
    private int timeoutSeconds = 15;
    private int maxRetries = 3;
    private int retryDelayMs = 500;
}