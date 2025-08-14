package com.pkfare.tripscale.dto;

import com.pkfare.tripscale.model.PersonalPreferences;
import com.pkfare.tripscale.model.TripRoute;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Response DTO for trip routes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripRoutesResponse {
    
    @NotBlank(message = "Session ID is required")
    private String sessionId;
    
    @Valid
    private List<TripRoute> routes;
    
    @Valid
    private PersonalPreferences appliedPreferences;
    
    private String status;
}