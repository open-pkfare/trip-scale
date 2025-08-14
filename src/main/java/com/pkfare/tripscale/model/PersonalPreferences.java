package com.pkfare.tripscale.model;

import lombok.Data;
import java.util.List;

/**
 * Domain model representing user's personal preferences for travel
 */
@Data
public class PersonalPreferences {
    
    private List<String> likes;
    private List<String> hates;
    
    public PersonalPreferences() {}
    
    public PersonalPreferences(List<String> likes, List<String> hates) {
        this.likes = likes;
        this.hates = hates;
    }
    

}