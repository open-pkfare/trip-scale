package com.pkfare.tripscale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Request DTO for destination confirmation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationConfirmationRequest {
    
    @NotBlank(message = "Session ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "Session ID contains invalid characters")
    @Size(min = 1, max = 100, message = "Session ID must be between 1 and 100 characters")
    private String sessionId;
    
    @NotBlank(message = "Destination is required")
    @Size(max = 100, message = "Destination name too long")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-'.,()]+$", message = "Destination contains invalid characters")
    private String destination;
    
    private boolean confirmed;
}