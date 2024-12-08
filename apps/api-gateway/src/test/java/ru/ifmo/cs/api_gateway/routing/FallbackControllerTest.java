package ru.ifmo.cs.api_gateway.routing;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FallbackControllerTest {

    private FallbackController fallbackController;

    @BeforeEach
    public void setUp() {
        fallbackController = new FallbackController();
    }

    @Test
    public void testFallback() {
        ResponseEntity<String> response = fallbackController.fallback();

        assertNotNull(response, "Response should not be null");
        assertEquals(200, response.getStatusCodeValue(), "Response status code should be 200 (OK)");
        assertEquals("This is simple fallback implementation.", response.getBody(),
                "Response body should match the expected fallback message");
    }
}