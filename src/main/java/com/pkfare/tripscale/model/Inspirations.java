package com.pkfare.tripscale.model;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Domain model representing user inspirations for travel recommendations
 */
@Data
public class Inspirations {
    
    @Valid
    private List<RecentFocus> recentFocus;
    
    @Valid
    private List<LastVisit> last5YearVisits;
    
    private List<String> travelStyle;
    
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;
    
    public Inspirations() {}
    
    public Inspirations(List<RecentFocus> recentFocus, List<LastVisit> last5YearVisits, 
                       List<String> travelStyle, Integer age) {
        this.recentFocus = recentFocus;
        this.last5YearVisits = last5YearVisits;
        this.travelStyle = travelStyle;
        this.age = age;
    }
    

}