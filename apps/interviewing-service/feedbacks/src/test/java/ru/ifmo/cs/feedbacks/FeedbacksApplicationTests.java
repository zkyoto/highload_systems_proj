package ru.ifmo.cs.feedbacks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest
class FeedbacksApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(FeedbacksApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
