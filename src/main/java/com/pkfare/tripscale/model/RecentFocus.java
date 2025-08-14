package com.pkfare.tripscale.model;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Model representing recent focus destination with priority
 */
@Data
public class RecentFocus {
    
    @NotNull(message = "Priority is required")
    @Min(value = 1, message = "Priority must be at least 1")
    private Integer priority;
    
    @NotBlank(message = "Destination is required")
    private String destination;
    
    public RecentFocus() {}
    
    public RecentFocus(Integer priority, String destination) {
        this.priority = priority;
        this.destination = destination;
    }
    

}