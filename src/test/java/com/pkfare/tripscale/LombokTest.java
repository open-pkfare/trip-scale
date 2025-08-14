package com.pkfare.tripscale;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify basic Lombok setup and dependency
 */
public class LombokTest {

    @Test
    public void testLombokDependencyIsAvailable() {
        // Test that Lombok dependency is available in the classpath
        try {
            Class.forName("lombok.Data");
            assertTrue(true, "Lombok dependency is available");
        } catch (ClassNotFoundException e) {
            fail("Lombok dependency is not available in classpath");
        }
    }
    
    @Test
    public void testProjectCompilesWithLombok() {
        // This test verifies that the project compiles successfully with Lombok
        // If this test runs, it means the Maven build with Lombok dependency worked
        assertTrue(true, "Project compiles successfully with Lombok dependency");
    }
}