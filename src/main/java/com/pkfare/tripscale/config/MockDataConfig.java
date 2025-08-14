package com.pkfare.tripscale.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Arrays;

/**
 * Configuration for mock data used in MemoryService implementation
 * Allows customization of mock data through application properties
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mock.memory")
public class MockDataConfig {
    
    private UserData userData = new UserData();
    
    @Data
    public static class UserData {
        private List<String> recentFocusDestinations = Arrays.asList("Japan", "Europe", "Southeast Asia");
        private List<String> travelStyles = Arrays.asList("Cultural", "Adventure", "Relaxation", "Food & Drink");
        private List<String> likes = Arrays.asList(
            "Museums and galleries", 
            "Local cuisine", 
            "Nature and hiking", 
            "Photography", 
            "Historical sites",
            "Local markets",
            "Beach activities"
        );
        private List<String> hates = Arrays.asList(
            "Crowded tourist traps", 
            "Extreme sports", 
            "Long flights over 12 hours",
            "Very expensive restaurants",
            "Rainy weather destinations"
        );
        private int age = 28;
    }
}