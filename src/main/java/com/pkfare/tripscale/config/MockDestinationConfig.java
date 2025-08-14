package com.pkfare.tripscale.config;

import com.pkfare.tripscale.dto.DestinationSuggestion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for mock destination data.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mock.destinations")
public class MockDestinationConfig {

  private List<DestinationSuggestion> suggestions;
}