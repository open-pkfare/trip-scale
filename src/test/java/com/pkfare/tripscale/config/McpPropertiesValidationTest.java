package com.pkfare.tripscale.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for McpProperties validation constraints
 */
class McpPropertiesValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidProperties() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");
        properties.setEnabled(true);
        properties.setMaxSuggestions(5);
        properties.setRequestTimeoutSeconds(10);
        properties.setLoggingEnabled(true);

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWithBlankName() {
        McpProperties properties = new McpProperties();
        properties.setName("");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("MCP server name is required");
    }

    @Test
    void shouldFailValidationWithBlankVersion() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("");
        properties.setDescription("Valid description");

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("MCP server version is required");
    }

    @Test
    void shouldFailValidationWithBlankDescription() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("");

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("MCP server description is required");
    }

    @Test
    void shouldFailValidationWithInvalidMaxSuggestions() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");
        properties.setMaxSuggestions(0);

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Maximum suggestions must be at least 1");
    }

    @Test
    void shouldFailValidationWithInvalidRequestTimeout() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");
        properties.setRequestTimeoutSeconds(0);

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Request timeout must be at least 1 second");
    }

    @Test
    void shouldFailValidationWithNullEnabled() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");
        properties.setEnabled(null);

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("MCP server enabled flag is required");
    }

    @Test
    void shouldFailValidationWithNullLoggingEnabled() {
        McpProperties properties = new McpProperties();
        properties.setName("valid-name");
        properties.setVersion("1.0.0");
        properties.setDescription("Valid description");
        properties.setLoggingEnabled(null);

        Set<ConstraintViolation<McpProperties>> violations = validator.validate(properties);
        
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("MCP logging enabled flag is required");
    }
}