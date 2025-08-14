package com.pkfare.tripscale.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for McpProperties configuration binding and validation
 */
@SpringBootTest
@TestPropertySource(properties = {
    "mcp.server.name=test-mcp-server",
    "mcp.server.version=2.0.0",
    "mcp.server.description=Test MCP server",
    "mcp.server.enabled=true",
    "mcp.server.max-suggestions=5",
    "mcp.server.request-timeout-seconds=15",
    "mcp.server.logging-enabled=false"
})
class McpPropertiesTest {

    @Autowired
    private McpProperties mcpProperties;

    @Test
    void shouldBindConfigurationProperties() {
        assertThat(mcpProperties.getName()).isEqualTo("test-mcp-server");
        assertThat(mcpProperties.getVersion()).isEqualTo("2.0.0");
        assertThat(mcpProperties.getDescription()).isEqualTo("Test MCP server");
        assertThat(mcpProperties.getEnabled()).isTrue();
        assertThat(mcpProperties.getMaxSuggestions()).isEqualTo(5);
        assertThat(mcpProperties.getRequestTimeoutSeconds()).isEqualTo(15);
        assertThat(mcpProperties.getLoggingEnabled()).isFalse();
    }

    @Test
    void shouldHaveDefaultValues() {
        // Test with default application.yml values by creating new instance
        McpProperties defaultProperties = new McpProperties();
        
        assertThat(defaultProperties.getName()).isEqualTo("tripscale-destination-recommendations");
        assertThat(defaultProperties.getVersion()).isEqualTo("1.0.0");
        assertThat(defaultProperties.getDescription()).isEqualTo("TripScale destination recommendation MCP service");
        assertThat(defaultProperties.getEnabled()).isTrue();
        assertThat(defaultProperties.getMaxSuggestions()).isEqualTo(10);
        assertThat(defaultProperties.getRequestTimeoutSeconds()).isEqualTo(30);
        assertThat(defaultProperties.getLoggingEnabled()).isTrue();
    }
}