package com.pkfare.tripscale.model;

import lombok.Data;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Domain model representing a trip route recommendation
 */
@Data
public class TripRoute {
    
    @NotBlank(message = "Route ID is required")
    private String routeId;
    
    @NotEmpty(message = "Destinations cannot be empty")
    private List<String> destinations;
    
    @NotNull(message = "Recommended days is required")
    @Min(value = 1, message = "Recommended days must be at least 1")
    private Integer recommendedDays;
    
    private String estimatedBudget;
    
    private List<String> highlights;
    
    @DecimalMin(value = "0.0", message = "Match score must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Match score must be at most 1.0")
    private Double matchScore;
    
    public TripRoute() {}
    
    public TripRoute(String routeId, List<String> destinations, Integer recommendedDays, 
                    String estimatedBudget, List<String> highlights, Double matchScore) {
        this.routeId = routeId;
        this.destinations = destinations;
        this.recommendedDays = recommendedDays;
        this.estimatedBudget = estimatedBudget;
        this.highlights = highlights;
        this.matchScore = matchScore;
    }
    

}