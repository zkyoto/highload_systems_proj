package ru.itmo.cs;

import org.springframework.boot.SpringApplication;
import ru.itmo.cs.app.interviewing.InterviewServiceApplication;

public class TestAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(InterviewServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
