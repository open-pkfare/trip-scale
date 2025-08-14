package com.pkfare.tripscale.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Response DTO for GuessMe feature
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuessMeResponse {
    
    @NotBlank(message = "Session ID is required")
    private String sessionId;
    
    @Valid
    private List<DestinationSuggestion> suggestions;
    
    private String message;
}