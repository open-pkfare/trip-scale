package com.pkfare.tripscale.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Request DTO for travel demand input
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelDemandRequest {
    
    @NotEmpty(message = "Must-go destinations cannot be empty")
    @Size(min = 1, max = 10, message = "Must have between 1 and 10 destinations")
    private List<@NotBlank(message = "Destination cannot be blank") 
                 @Size(max = 100, message = "Destination name too long") String> mustGoDestinations;
    
    @NotNull(message = "Days is required")
    @Min(value = 1, message = "Days must be at least 1")
    @Max(value = 365, message = "Days cannot exceed 365")
    private Integer days;
    
    @NotNull(message = "Passenger count is required")
    @Min(value = 1, message = "Passenger count must be at least 1")
    @Max(value = 50, message = "Passenger count cannot exceed 50")
    private Integer passenger;
    
    @NotBlank(message = "Passenger type is required")
    @Pattern(regexp = "^(adult|child|senior|family|group)$", 
             message = "Passenger type must be one of: adult, child, senior, family, group")
    private String passengerType;
    
    @NotBlank(message = "Budget is required")
    @Pattern(regexp = "^(low|medium|high|luxury)$", 
             message = "Budget must be one of: low, medium, high, luxury")
    private String budgets;
    
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "User ID contains invalid characters")
    @Size(min = 1, max = 50, message = "User ID must be between 1 and 50 characters")
    private String userId;
}