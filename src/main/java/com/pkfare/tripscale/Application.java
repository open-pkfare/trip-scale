package com.pkfare.tripscale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main application class for the Spring Boot Framework.
 * This class serves as the entry point for the Spring Boot application.
 */
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.pkfare.*"}, exclude = {MongoDataAutoConfiguration.class, MongoAutoConfiguration.class})
public class Application {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}