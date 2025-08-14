package com.pkfare.tripscale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Configuration properties for MCP (Model Context Protocol) server integration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mcp.server")
@Validated
public class McpProperties {
    
    /**
     * MCP server name for identification
     */
    @NotBlank(message = "MCP server name is required")
    private String name = "tripscale-destination-recommendations";
    
    /**
     * MCP server version
     */
    @NotBlank(message = "MCP server version is required")
    private String version = "1.0.0";
    
    /**
     * MCP server description
     */
    @NotBlank(message = "MCP server description is required")
    private String description = "TripScale destination recommendation MCP service";
    
    /**
     * Whether the MCP server is enabled
     */
    @NotNull(message = "MCP server enabled flag is required")
    private Boolean enabled = true;
    
    /**
     * Maximum number of suggestions to return per request
     */
    @Min(value = 1, message = "Maximum suggestions must be at least 1")
    private int maxSuggestions = 10;
    
    /**
     * Request timeout in seconds
     */
    @Min(value = 1, message = "Request timeout must be at least 1 second")
    private int requestTimeoutSeconds = 30;
    
    /**
     * Whether to enable detailed logging for MCP requests
     */
    @NotNull(message = "MCP logging enabled flag is required")
    private Boolean loggingEnabled = true;
}