package ru.ifmo.cs.interview_results;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest
class InterviewResultsApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(InterviewResultsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
