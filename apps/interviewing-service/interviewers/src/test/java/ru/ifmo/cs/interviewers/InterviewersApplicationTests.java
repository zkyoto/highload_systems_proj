package ru.ifmo.cs.interviewers;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

class InterviewersApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(InterviewersApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
