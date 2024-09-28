package ru.itmo.cs;

import org.springframework.boot.SpringApplication;
import ru.itmo.cs.app.interviewing.AppApplication;

public class TestAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(AppApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
