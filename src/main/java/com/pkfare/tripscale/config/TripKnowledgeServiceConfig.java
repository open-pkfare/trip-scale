package com.pkfare.tripscale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Trip Knowledge Base service integration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "external.trip-knowledge")
public class TripKnowledgeServiceConfig {
    
    private String baseUrl;
    private String apiKey;
    private int timeoutSeconds = 20;
    private int maxRetries = 3;
    private int retryDelayMs = 1000;
}