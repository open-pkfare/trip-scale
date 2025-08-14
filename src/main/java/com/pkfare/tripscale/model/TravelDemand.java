package com.pkfare.tripscale.model;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Domain model representing travel demand information
 */
@Data
public class TravelDemand {
    
    @NotEmpty(message = "Must-go destinations cannot be empty")
    private List<String> mustGoDestinations;
    
    @NotNull(message = "Days is required")
    @Min(value = 1, message = "Days must be at least 1")
    private Integer days;
    
    @NotNull(message = "Passenger count is required")
    @Min(value = 1, message = "Passenger count must be at least 1")
    private Integer passenger;
    
    @NotBlank(message = "Passenger type is required")
    private String passengerType;
    
    @NotBlank(message = "Budget is required")
    private String budgets;
    
    private String sessionId;
    
    public TravelDemand() {}
    
    public TravelDemand(List<String> mustGoDestinations, Integer days, Integer passenger, 
                       String passengerType, String budgets, String sessionId) {
        this.mustGoDestinations = mustGoDestinations;
        this.days = days;
        this.passenger = passenger;
        this.passengerType = passengerType;
        this.budgets = budgets;
        this.sessionId = sessionId;
    }
    

}