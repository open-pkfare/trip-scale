package com.pkfare.tripscale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Request DTO for initiating GuessMe feature
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuessMeRequest {
    
    @NotBlank(message = "User ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "User ID contains invalid characters")
    @Size(min = 1, max = 50, message = "User ID must be between 1 and 50 characters")
    private String userId;
}