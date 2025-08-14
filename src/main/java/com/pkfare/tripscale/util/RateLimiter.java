package com.pkfare.tripscale.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate limiter for external service calls to prevent abuse and ensure
 * fair usage of external APIs.
 */
@Slf4j
@Component
public class RateLimiter {
    
    // Rate limit configurations
    private static final int DIFY_REQUESTS_PER_MINUTE = 30;
    private static final int MEMORY_REQUESTS_PER_MINUTE = 60;
    private static final int TRIP_KNOWLEDGE_REQUESTS_PER_MINUTE = 100;
    
    // Storage for rate limit counters
    private final ConcurrentHashMap<String, RateLimitInfo> rateLimits = new ConcurrentHashMap<>();
    
    /**
     * Checks if a request is allowed for the given service and user.
     * 
     * @param service The service name (dify, memory, trip-knowledge)
     * @param userId The user ID making the request
     * @return true if request is allowed, false if rate limit exceeded
     */
    public boolean isRequestAllowed(String service, String userId) {
        String key = service + ":" + userId;
        int maxRequests = getMaxRequestsForService(service);
        
        log.debug("Checking rate limit for service: {}, user: {}, max requests: {}", service, userId, maxRequests);
        
        RateLimitInfo info = rateLimits.computeIfAbsent(key, k -> new RateLimitInfo());
        
        synchronized (info) {
            LocalDateTime now = LocalDateTime.now();
            
            // Reset counter if a minute has passed
            if (info.windowStart == null || 
                ChronoUnit.MINUTES.between(info.windowStart, now) >= 1) {
                if (info.windowStart != null) {
                    log.debug("Rate limit window reset for service: {}, user: {}", service, userId);
                }
                info.windowStart = now;
                info.requestCount.set(0);
            }
            
            // Check if under limit
            if (info.requestCount.get() < maxRequests) {
                int currentCount = info.requestCount.incrementAndGet();
                log.debug("Request allowed for service: {}, user: {}, current count: {}/{}", 
                         service, userId, currentCount, maxRequests);
                return true;
            }
            
            // Rate limit exceeded - log warning
            log.warn("Rate limit exceeded for service: {}, user: {}, requests: {}/{}, window started: {}", 
                    service, userId, info.requestCount.get(), maxRequests, info.windowStart);
            return false;
        }
    }
    
    /**
     * Gets the remaining requests for a service and user.
     * 
     * @param service The service name
     * @param userId The user ID
     * @return Number of remaining requests in current window
     */
    public int getRemainingRequests(String service, String userId) {
        String key = service + ":" + userId;
        int maxRequests = getMaxRequestsForService(service);
        
        log.debug("Getting remaining requests for service: {}, user: {}", service, userId);
        
        RateLimitInfo info = rateLimits.get(key);
        if (info == null) {
            log.debug("No rate limit info found for service: {}, user: {}, returning max: {}", 
                     service, userId, maxRequests);
            return maxRequests;
        }
        
        synchronized (info) {
            LocalDateTime now = LocalDateTime.now();
            
            // Reset if window expired
            if (info.windowStart == null || 
                ChronoUnit.MINUTES.between(info.windowStart, now) >= 1) {
                log.debug("Rate limit window expired for service: {}, user: {}, returning max: {}", 
                         service, userId, maxRequests);
                return maxRequests;
            }
            
            int remaining = Math.max(0, maxRequests - info.requestCount.get());
            log.debug("Remaining requests for service: {}, user: {}: {}/{}", 
                     service, userId, remaining, maxRequests);
            return remaining;
        }
    }
    
    /**
     * Gets the time until the rate limit window resets.
     * 
     * @param service The service name
     * @param userId The user ID
     * @return Seconds until reset, or 0 if already reset
     */
    public long getSecondsUntilReset(String service, String userId) {
        String key = service + ":" + userId;
        RateLimitInfo info = rateLimits.get(key);
        
        log.debug("Getting seconds until reset for service: {}, user: {}", service, userId);
        
        if (info == null || info.windowStart == null) {
            log.debug("No rate limit info or window start for service: {}, user: {}, returning 0", service, userId);
            return 0;
        }
        
        synchronized (info) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime resetTime = info.windowStart.plusMinutes(1);
            
            if (now.isAfter(resetTime)) {
                log.debug("Rate limit window already expired for service: {}, user: {}, returning 0", service, userId);
                return 0;
            }
            
            long secondsUntilReset = ChronoUnit.SECONDS.between(now, resetTime);
            log.debug("Seconds until reset for service: {}, user: {}: {}", service, userId, secondsUntilReset);
            return secondsUntilReset;
        }
    }
    
    private int getMaxRequestsForService(String service) {
        int maxRequests = switch (service.toLowerCase()) {
            case "dify" -> DIFY_REQUESTS_PER_MINUTE;
            case "memory" -> MEMORY_REQUESTS_PER_MINUTE;
            case "trip-knowledge" -> TRIP_KNOWLEDGE_REQUESTS_PER_MINUTE;
            default -> {
                log.warn("Unknown service '{}' requested, using default rate limit of 10 requests per minute", service);
                yield 10; // Default conservative limit
            }
        };
        
        log.debug("Rate limit configuration for service '{}': {} requests per minute", service, maxRequests);
        return maxRequests;
    }
    
    /**
     * Internal class to track rate limit information
     */
    private static class RateLimitInfo {
        private LocalDateTime windowStart;
        private final AtomicInteger requestCount = new AtomicInteger(0);
    }
}