package com.pkfare.tripscale.service;

import com.pkfare.tripscale.model.PersonalPreferences;
import com.pkfare.tripscale.model.TravelDemand;
import com.pkfare.tripscale.model.TripRoute;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Session data holder for multi-step travel interactions
 */
@Slf4j
public class SessionManager {
    
    /**
     * Session data for tracking multi-step travel workflows
     */
    public static class SessionData {
        private String sessionId;
        private String userId;
        private LocalDateTime createdAt;
        private LocalDateTime lastAccessedAt;
        private String selectedDestination;
        private TravelDemand travelDemand;
        private PersonalPreferences personalPreferences;
        private List<TripRoute> tripRoutes;
        private SessionStatus status;
        
        public SessionData(String sessionId, String userId) {
            this.sessionId = sessionId;
            this.userId = userId;
            this.createdAt = LocalDateTime.now();
            this.lastAccessedAt = LocalDateTime.now();
            this.status = SessionStatus.INITIATED;
            log.debug("Created new session: {} for user: {}", sessionId, userId);
        }
        
        public void updateLastAccessed() {
            this.lastAccessedAt = LocalDateTime.now();
            log.debug("Updated last accessed time for session: {}", this.sessionId);
        }
        
        // Getters and setters
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getLastAccessedAt() { return lastAccessedAt; }
        public void setLastAccessedAt(LocalDateTime lastAccessedAt) { this.lastAccessedAt = lastAccessedAt; }
        
        public String getSelectedDestination() { return selectedDestination; }
        public void setSelectedDestination(String selectedDestination) { this.selectedDestination = selectedDestination; }
        
        public TravelDemand getTravelDemand() { return travelDemand; }
        public void setTravelDemand(TravelDemand travelDemand) { this.travelDemand = travelDemand; }
        
        public PersonalPreferences getPersonalPreferences() { return personalPreferences; }
        public void setPersonalPreferences(PersonalPreferences personalPreferences) { this.personalPreferences = personalPreferences; }
        
        public List<TripRoute> getTripRoutes() { return tripRoutes; }
        public void setTripRoutes(List<TripRoute> tripRoutes) { this.tripRoutes = tripRoutes; }
        
        public SessionStatus getStatus() { return status; }
        public void setStatus(SessionStatus status) { 
            SessionStatus oldStatus = this.status;
            this.status = status; 
            log.debug("Session {} status changed from {} to {}", this.sessionId, oldStatus, status);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SessionData that = (SessionData) o;
            return Objects.equals(sessionId, that.sessionId);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(sessionId);
        }
    }
    
    /**
     * Session status enumeration
     */
    public enum SessionStatus {
        INITIATED,
        DESTINATION_CONFIRMED,
        DETAILS_COLLECTED,
        ROUTES_FOUND,
        COMPLETED,
        EXPIRED
    }
    
    /**
     * Generate a unique session ID
     * 
     * @return Unique session identifier
     */
    public static String generateSessionId() {
        String sessionId = "session_" + System.currentTimeMillis() + "_" + 
               (int)(Math.random() * 10000);
        log.debug("Generated new session ID: {}", sessionId);
        return sessionId;
    }
}