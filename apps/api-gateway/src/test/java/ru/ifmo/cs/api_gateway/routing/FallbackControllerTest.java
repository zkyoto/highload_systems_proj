package ru.ifmo.cs.api_gateway.routing;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.ifmo.cs.api_gateway.routing.presentation.FallbackController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FallbackControllerTest {

    private FallbackController fallbackController;

    @BeforeEach
    void setUp() {
        fallbackController = new FallbackController();
    }

    @Test
    void testFallback() {
        ResponseEntity<?> response = fallbackController.fallback();

        assertNotNull(response, "Response should not be null");
        assertEquals(503, response.getStatusCodeValue(), "Response status code should be 503");
    }
}