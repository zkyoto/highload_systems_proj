package ru.ifmo.cs.candidates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest
class CandidatesApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(CandidatesApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
