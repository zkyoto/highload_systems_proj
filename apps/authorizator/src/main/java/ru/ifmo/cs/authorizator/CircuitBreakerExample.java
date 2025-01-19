package ru.ifmo.cs.authorizator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerExample {
    private boolean isAvailable = true;

    @GetMapping("/availability")
    public ResponseEntity<String> getAvailability() {
        if (isAvailable) {
            return ResponseEntity.ok("Available");
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/switch")
    public ResponseEntity<String> switchAvailability() {
        isAvailable = !isAvailable;
        return ResponseEntity.ok().build();
    }
}
