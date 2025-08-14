package com.pkfare.tripscale.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class for sanitizing user input to prevent injection attacks
 * and ensure data integrity.
 */
@Slf4j
@Component
public class InputSanitizer {
    
    // Pattern to match potentially dangerous characters
    private static final Pattern DANGEROUS_CHARS = Pattern.compile("[<>\"'&;\\\\]");
    
    // Pattern for valid destination names (letters, numbers, spaces, hyphens, apostrophes)
    private static final Pattern VALID_DESTINATION = Pattern.compile("^[a-zA-Z0-9\\s\\-'.,()]+$");
    
    // Pattern for valid user IDs (alphanumeric and hyphens)
    private static final Pattern VALID_USER_ID = Pattern.compile("^[a-zA-Z0-9\\-_]+$");
    
    // Pattern for valid session IDs (alphanumeric and hyphens)
    private static final Pattern VALID_SESSION_ID = Pattern.compile("^[a-zA-Z0-9\\-_]+$");
    
    // Maximum length for destination names
    private static final int MAX_DESTINATION_LENGTH = 100;
    
    // Maximum length for user preferences
    private static final int MAX_PREFERENCE_LENGTH = 50;
    
    /**
     * Sanitizes a destination name by removing dangerous characters
     * and validating format.
     * 
     * @param destination The destination name to sanitize
     * @return Sanitized destination name
     * @throws IllegalArgumentException if destination is invalid
     */
    public String sanitizeDestination(String destination) {
        log.debug("Sanitizing destination input: {}", destination != null ? destination.length() + " characters" : "null");
        
        if (destination == null || destination.trim().isEmpty()) {
            log.warn("Attempted to sanitize null or empty destination");
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }
        
        String trimmed = destination.trim();
        
        if (trimmed.length() > MAX_DESTINATION_LENGTH) {
            log.warn("Destination name exceeds maximum length: {} characters (max {})", trimmed.length(), MAX_DESTINATION_LENGTH);
            throw new IllegalArgumentException("Destination name too long (max " + MAX_DESTINATION_LENGTH + " characters)");
        }
        
        // Remove dangerous characters first
        String sanitized = DANGEROUS_CHARS.matcher(trimmed).replaceAll("");
        
        // Log security event if dangerous characters were found
        if (!sanitized.equals(trimmed)) {
            log.warn("Security: Dangerous characters detected and removed from destination input. Original length: {}, Sanitized length: {}", 
                    trimmed.length(), sanitized.length());
        }
        
        // Then validate the sanitized result
        if (!VALID_DESTINATION.matcher(sanitized).matches()) {
            log.warn("Security: Destination contains invalid characters after sanitization: {}", sanitized);
            throw new IllegalArgumentException("Destination contains invalid characters");
        }
        
        log.debug("Successfully sanitized destination");
        return sanitized;
    }
    
    /**
     * Sanitizes a list of destinations.
     * 
     * @param destinations List of destination names to sanitize
     * @return List of sanitized destination names
     */
    public List<String> sanitizeDestinations(List<String> destinations) {
        if (destinations == null) {
            log.debug("Sanitizing null destinations list");
            return null;
        }
        
        log.debug("Sanitizing {} destinations", destinations.size());
        
        List<String> sanitized = destinations.stream()
                .map(this::sanitizeDestination)
                .collect(Collectors.toList());
        
        log.debug("Successfully sanitized {} destinations", sanitized.size());
        return sanitized;
    }
    
    /**
     * Sanitizes a user ID by validating format.
     * 
     * @param userId The user ID to sanitize
     * @return Sanitized user ID
     * @throws IllegalArgumentException if user ID is invalid
     */
    public String sanitizeUserId(String userId) {
        log.debug("Sanitizing user ID input");
        
        if (userId == null || userId.trim().isEmpty()) {
            log.warn("Security: Attempted to sanitize null or empty user ID");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        
        String trimmed = userId.trim();
        
        if (!VALID_USER_ID.matcher(trimmed).matches()) {
            log.warn("Security: Invalid user ID format detected - contains invalid characters");
            throw new IllegalArgumentException("User ID contains invalid characters");
        }
        
        log.debug("Successfully sanitized user ID");
        return trimmed;
    }
    
    /**
     * Sanitizes a session ID by validating format.
     * 
     * @param sessionId The session ID to sanitize
     * @return Sanitized session ID
     * @throws IllegalArgumentException if session ID is invalid
     */
    public String sanitizeSessionId(String sessionId) {
        log.debug("Sanitizing session ID input");
        
        if (sessionId == null || sessionId.trim().isEmpty()) {
            log.warn("Security: Attempted to sanitize null or empty session ID");
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }
        
        String trimmed = sessionId.trim();
        
        if (!VALID_SESSION_ID.matcher(trimmed).matches()) {
            log.warn("Security: Invalid session ID format detected - contains invalid characters");
            throw new IllegalArgumentException("Session ID contains invalid characters");
        }
        
        log.debug("Successfully sanitized session ID");
        return trimmed;
    }
    
    /**
     * Sanitizes a preference string by removing dangerous characters
     * and validating length.
     * 
     * @param preference The preference string to sanitize
     * @return Sanitized preference string
     * @throws IllegalArgumentException if preference is invalid
     */
    public String sanitizePreference(String preference) {
        log.debug("Sanitizing preference input: {}", preference != null ? preference.length() + " characters" : "null");
        
        if (preference == null) {
            return null;
        }
        
        String trimmed = preference.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        
        if (trimmed.length() > MAX_PREFERENCE_LENGTH) {
            log.warn("Preference exceeds maximum length: {} characters (max {})", trimmed.length(), MAX_PREFERENCE_LENGTH);
            throw new IllegalArgumentException("Preference too long (max " + MAX_PREFERENCE_LENGTH + " characters)");
        }
        
        // Remove dangerous characters
        String sanitized = DANGEROUS_CHARS.matcher(trimmed).replaceAll("");
        
        // Log security event if dangerous characters were found
        if (!sanitized.equals(trimmed)) {
            log.warn("Security: Dangerous characters detected and removed from preference input. Original length: {}, Sanitized length: {}", 
                    trimmed.length(), sanitized.length());
        }
        
        log.debug("Successfully sanitized preference");
        return sanitized;
    }
    
    /**
     * Sanitizes a list of preferences.
     * 
     * @param preferences List of preference strings to sanitize
     * @return List of sanitized preference strings
     */
    public List<String> sanitizePreferences(List<String> preferences) {
        if (preferences == null) {
            log.debug("Sanitizing null preferences list");
            return null;
        }
        
        log.debug("Sanitizing {} preferences", preferences.size());
        
        List<String> sanitized = preferences.stream()
                .map(this::sanitizePreference)
                .filter(pref -> pref != null && !pref.isEmpty())
                .collect(Collectors.toList());
        
        log.debug("Successfully sanitized {} preferences (filtered {} empty/null)", sanitized.size(), preferences.size() - sanitized.size());
        return sanitized;
    }
}