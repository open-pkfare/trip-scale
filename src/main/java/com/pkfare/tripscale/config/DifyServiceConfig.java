package com.pkfare.tripscale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Dify AI service integration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "external.dify")
public class DifyServiceConfig {
    
    private String baseUrl;
    private String apiKey;
    private int timeoutSeconds = 30;
    private int maxRetries = 3;
    private int retryDelayMs = 1000;
}