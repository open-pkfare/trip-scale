package com.pkfare.tripscale.service.impl;

import com.pkfare.tripscale.config.MockDataConfig;
import com.pkfare.tripscale.exception.ExternalServiceException;
import com.pkfare.tripscale.exception.UserNotFoundException;
import com.pkfare.tripscale.exception.ValidationException;
import com.pkfare.tripscale.model.*;
import com.pkfare.tripscale.service.MemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Simple implementation of MemoryService with configurable mock data
 * for API development and testing purposes.
 */
@Slf4j
@Service
public class MemoryServiceImpl implements MemoryService {
    
    @Autowired
    private MockDataConfig mockDataConfig;
    
    @Override
    public Inspirations getInspirations(String userId) {
        log.info("Retrieving inspirations for user: {}", userId);
        
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        
        try {
            // Simulate user not found scenario for specific test users
            if ("nonexistent".equals(userId)) {
                throw new UserNotFoundException(userId);
            }
            
            // Return configurable mock inspirations data
            log.debug("Creating mock inspirations data for user: {}", userId);
            Inspirations inspirations = new Inspirations();
        
        // Create recent focus data from configuration
        List<String> focusDestinations = mockDataConfig.getUserData().getRecentFocusDestinations();
        List<RecentFocus> recentFocusList = IntStream.range(0, focusDestinations.size())
                .mapToObj(i -> {
                    RecentFocus focus = new RecentFocus();
                    focus.setPriority(i + 1);
                    focus.setDestination(focusDestinations.get(i));
                    return focus;
                })
                .toList();
        
        inspirations.setRecentFocus(recentFocusList);
        
        // Mock last 5 year visits with predefined data
        LastVisit visit1 = new LastVisit();
        visit1.setDate("2023-06-15");
        visit1.setLocations(Arrays.asList("Bangkok", "Phuket", "Chiang Mai"));
        
        LastVisit visit2 = new LastVisit();
        visit2.setDate("2022-12-20");
        visit2.setLocations(Arrays.asList("London", "Edinburgh", "Dublin"));
        
        LastVisit visit3 = new LastVisit();
        visit3.setDate("2022-08-10");
        visit3.setLocations(Arrays.asList("Paris", "Lyon", "Nice"));
        
        inspirations.setLast5YearVisits(Arrays.asList(visit1, visit2, visit3));
        
            // Use configurable travel style and age
            inspirations.setTravelStyle(mockDataConfig.getUserData().getTravelStyles());
            inspirations.setAge(mockDataConfig.getUserData().getAge());
            
            log.debug("Successfully retrieved inspirations for user: {}", userId);
            return inspirations;
            
        } catch (UserNotFoundException e) {
            log.warn("User not found: {}", userId);
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving inspirations for user: {}", userId, e);
            throw new ExternalServiceException("MemoryService", "Failed to retrieve user inspirations", e);
        }
    }
    
    @Override
    public PersonalPreferences getPersonalPreferences(String userId) {
        log.info("Retrieving personal preferences for user: {}", userId);
        
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        
        try {
            // Simulate user not found scenario for specific test users
            if ("nonexistent".equals(userId)) {
                throw new UserNotFoundException(userId);
            }
            
            // Return configurable personal preferences
            log.debug("Creating mock personal preferences for user: {}", userId);
            PersonalPreferences preferences = new PersonalPreferences();
            preferences.setLikes(mockDataConfig.getUserData().getLikes());
            preferences.setHates(mockDataConfig.getUserData().getHates());
            log.debug("Personal preferences - Likes: {}, Hates: {}", 
                     preferences.getLikes().size(), preferences.getHates().size());
            
            log.debug("Successfully retrieved personal preferences for user: {}", userId);
            return preferences;
            
        } catch (UserNotFoundException e) {
            log.warn("User not found: {}", userId);
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving personal preferences for user: {}", userId, e);
            throw new ExternalServiceException("MemoryService", "Failed to retrieve user preferences", e);
        }
    }
    
    @Override
    public void updateUserHistory(String userId, TravelDemand travelDemand) {
        log.info("Updating user history for user: {}", userId);
        
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        
        if (travelDemand == null) {
            throw new ValidationException("Travel demand cannot be null");
        }
        
        try {
            // Simulate user not found scenario for specific test users
            if ("nonexistent".equals(userId)) {
                throw new UserNotFoundException(userId);
            }
            
            // Mock implementation - just log that we received the update
            log.debug("Mock MemoryService: Received travel demand update for user {}", userId);
            log.debug("Must-go destinations: {}", travelDemand.getMustGoDestinations());
            log.debug("Days: {}", travelDemand.getDays());
            log.debug("Passengers: {}", travelDemand.getPassenger());
            log.debug("Budget: {}", travelDemand.getBudgets());
            
            // In a real implementation, this would persist to storage
            log.info("Successfully updated user history for user: {}", userId);
            
        } catch (UserNotFoundException e) {
            log.warn("User not found during history update: {}", userId);
            throw e;
        } catch (ValidationException e) {
            log.warn("Validation error during history update: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating user history for user: {}", userId, e);
            throw new ExternalServiceException("MemoryService", "Failed to update user history", e);
        }
    }
    
    @Override
    public boolean isServiceHealthy() {
        log.debug("Checking MemoryService health - mock implementation always returns true");
        // Always return true for mock implementation
        return true;
    }
}