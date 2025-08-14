package com.pkfare.tripscale.model;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response model for API errors
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ErrorResponse {
    
    private String error;
    private String errorCode;
    private Integer status;
    private String path;
    private LocalDateTime timestamp;
    private List<String> details;
    
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ErrorResponse(String error, String errorCode, Integer status, String path) {
        this.error = error;
        this.errorCode = errorCode;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    
    public ErrorResponse(String error, String errorCode, Integer status, String path, List<String> details) {
        this.error = error;
        this.errorCode = errorCode;
        this.status = status;
        this.path = path;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
    

}