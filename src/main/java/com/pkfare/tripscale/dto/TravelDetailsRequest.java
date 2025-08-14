package com.pkfare.tripscale.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Request DTO for collecting travel details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelDetailsRequest {
    
    @NotBlank(message = "Session ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "Session ID contains invalid characters")
    @Size(min = 1, max = 100, message = "Session ID must be between 1 and 100 characters")
    private String sessionId;
    
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
}