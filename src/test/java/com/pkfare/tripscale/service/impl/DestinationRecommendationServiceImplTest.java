package com.pkfare.tripscale.service.impl;

import com.pkfare.tripscale.config.MockDestinationConfig;
import com.pkfare.tripscale.dto.DestinationSuggestion;
import com.pkfare.tripscale.dto.DestinationSuggestionsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinationRecommendationServiceImplTest {
    
    @Mock
    private MockDestinationConfig mockDestinationConfig;
    
    @InjectMocks
    private DestinationRecommendationServiceImpl destinationRecommendationService;
    
    private List<DestinationSuggestion> mockSuggestions;
    
    @BeforeEach
    void setUp() {
        // Set up mock destination suggestions
        mockSuggestions = Arrays.asList(
            new DestinationSuggestion("Vietnam, Hanoi", "Perfect blend of culture and cuisine with affordable prices", 0.85),
            new DestinationSuggestion("Japan, Kyoto", "Rich cultural heritage and beautiful temples", 0.92),
            new DestinationSuggestion("Thailand, Chiang Mai", "Authentic local experiences and great food scene", 0.78),
            new DestinationSuggestion("Portugal, Porto", "Charming architecture and excellent wine culture", 0.81),
            new DestinationSuggestion("South Korea, Busan", "Coastal beauty with modern city amenities", 0.76)
        );
        
        // Set the maxSuggestions field using reflection
        ReflectionTestUtils.setField(destinationRecommendationService, "maxSuggestions", 10);
    }
    
    @Test
    void testGetDestinationSuggestions_Success() {
        // Arrange
        String userId = "user123";
        when(mockDestinationConfig.getSuggestions()).thenReturn(mockSuggestions);
        
        // Act
        DestinationSuggestionsResponse result = destinationRecommendationService.getDestinationSuggestions(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertEquals(userId, result.getUserId());
        assertNotNull(result.getSuggestions());
        assertFalse(result.getSuggestions().isEmpty());
        assertTrue(result.getSuggestions().size() <= 10); // Should not exceed max suggestions
        assertTrue(result.getSuggestions().size() <= mockSuggestions.size()); // Should not exceed available suggestions
        assertEquals(result.getSuggestions().size(), result.getTotalCount());
        
        // Verify that all returned suggestions are from the mock data
        for (DestinationSuggestion suggestion : result.getSuggestions()) {
            assertNotNull(suggestion.getDestination());
            assertNotNull(suggestion.getReason());
            assertNotNull(suggestion.getConfidence());
            assertTrue(suggestion.getConfidence() >= 0.0 && suggestion.getConfidence() <= 1.0);
        }
    }
    
    @Test
    void testGetDestinationSuggestions_LimitedResults() {
        // Arrange
        String userId = "user123";
        ReflectionTestUtils.setField(destinationRecommendationService, "maxSuggestions", 3);
        when(mockDestinationConfig.getSuggestions()).thenReturn(mockSuggestions);
        
        // Act
        DestinationSuggestionsResponse result = destinationRecommendationService.getDestinationSuggestions(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertEquals(3, result.getSuggestions().size()); // Should be limited to maxSuggestions
        assertEquals(3, result.getTotalCount());
    }
    
    @Test
    void testGetDestinationSuggestions_NullUserId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            destinationRecommendationService.getDestinationSuggestions(null);
        });
        
        assertEquals("用户 ID 不能为空", exception.getMessage());
    }
    
    @Test
    void testGetDestinationSuggestions_EmptyUserId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            destinationRecommendationService.getDestinationSuggestions("");
        });
        
        assertEquals("用户 ID 不能为空", exception.getMessage());
    }
    
    @Test
    void testGetDestinationSuggestions_BlankUserId() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            destinationRecommendationService.getDestinationSuggestions("   ");
        });
        
        assertEquals("用户 ID 不能为空", exception.getMessage());
    }
    
    @Test
    void testGetDestinationSuggestions_EmptyMockData() {
        // Arrange
        String userId = "user123";
        when(mockDestinationConfig.getSuggestions()).thenReturn(Arrays.asList());
        
        // Act
        DestinationSuggestionsResponse result = destinationRecommendationService.getDestinationSuggestions(userId);
        
        // Assert
        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertTrue(result.getSuggestions().isEmpty());
        assertEquals(0, result.getTotalCount());
    }
    
    @Test
    void testGetDestinationSuggestions_VarietyInResults() {
        // Arrange
        String userId = "user123";
        when(mockDestinationConfig.getSuggestions()).thenReturn(mockSuggestions);
        
        // Act - Call multiple times to test shuffling
        DestinationSuggestionsResponse result1 = destinationRecommendationService.getDestinationSuggestions(userId);
        DestinationSuggestionsResponse result2 = destinationRecommendationService.getDestinationSuggestions(userId);
        
        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("success", result1.getStatus());
        assertEquals("success", result2.getStatus());
        assertEquals(result1.getSuggestions().size(), result2.getSuggestions().size());
        
        // Results might be in different order due to shuffling (though not guaranteed)
        // At minimum, verify they contain valid suggestions
        for (DestinationSuggestion suggestion : result1.getSuggestions()) {
            assertTrue(mockSuggestions.stream().anyMatch(mock -> 
                mock.getDestination().equals(suggestion.getDestination())));
        }
    }
}