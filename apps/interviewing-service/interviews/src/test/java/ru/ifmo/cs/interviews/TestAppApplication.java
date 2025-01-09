package ru.ifmo.cs.interviews;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(InterviewsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
