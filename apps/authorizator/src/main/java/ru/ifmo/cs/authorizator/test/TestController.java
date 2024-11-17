package ru.ifmo.cs.authorizator.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final String answer;

    public TestController(
            @Value("${test.answer}") String answer
    ) {
        this.answer = answer;
    }

    @GetMapping("/test")
    public String test() {
        return answer;
    }
}
