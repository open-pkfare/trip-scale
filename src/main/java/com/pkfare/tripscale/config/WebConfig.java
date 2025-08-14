package com.pkfare.tripscale.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class for Spring Boot framework.
 * Configures CORS settings, logging interceptors, and other web-specific configurations.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    /**
     * Configure CORS settings for cross-origin requests.
     * Allows requests from different origins to access the API endpoints.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Configuring CORS mappings for /api/** endpoints");
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * Register interceptors for request/response logging and other cross-cutting concerns.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Registering logging interceptor for /api/** paths");
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**");
    }

    /**
     * Configure request logging filter for detailed request logging.
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        log.info("Configuring request logging filter with payload logging enabled");
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }
}