package com.pkfare.tripscale.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class HealthController {
    


    @GetMapping("/health")
    public Map<String, Object> health() {
        log.debug("Health check endpoint accessed");
        
        try {
            Map<String, Object> healthInfo = new HashMap<>();
            
            // Basic health status
            healthInfo.put("status", "UP");
            healthInfo.put("timestamp", LocalDateTime.now());
            
            // Basic system information
            Map<String, Object> systemInfo = new HashMap<>();
            systemInfo.put("javaVersion", System.getProperty("java.version"));
            systemInfo.put("osName", System.getProperty("os.name"));
            systemInfo.put("osVersion", System.getProperty("os.version"));
            systemInfo.put("availableProcessors", Runtime.getRuntime().availableProcessors());
            systemInfo.put("maxMemory", Runtime.getRuntime().maxMemory());
            systemInfo.put("freeMemory", Runtime.getRuntime().freeMemory());
            
            healthInfo.put("system", systemInfo);
            
            // Application information
            Map<String, Object> appInfo = new HashMap<>();
            appInfo.put("name", "tripscale-application");
            appInfo.put("version", "1.0.0");
            
            healthInfo.put("application", appInfo);
            
            log.info("Health check completed successfully - Status: UP, Free Memory: {} MB", 
                    Runtime.getRuntime().freeMemory() / (1024 * 1024));
            
            return healthInfo;
        } catch (Exception e) {
            log.error("Error during health check", e);
            
            Map<String, Object> errorHealthInfo = new HashMap<>();
            errorHealthInfo.put("status", "DOWN");
            errorHealthInfo.put("timestamp", LocalDateTime.now());
            errorHealthInfo.put("error", "Health check failed");
            
            return errorHealthInfo;
        }
    }
}