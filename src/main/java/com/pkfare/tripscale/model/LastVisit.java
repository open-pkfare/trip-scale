package com.pkfare.tripscale.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Model representing last visit information with date and locations
 */
@Data
public class LastVisit {
    
    @NotBlank(message = "Date is required")
    private String date;
    
    @NotEmpty(message = "Locations cannot be empty")
    private List<String> locations;
    
    public LastVisit() {}
    
    public LastVisit(String date, List<String> locations) {
        this.date = date;
        this.locations = locations;
    }
    

}