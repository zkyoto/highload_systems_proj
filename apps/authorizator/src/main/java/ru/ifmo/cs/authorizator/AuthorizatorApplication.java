package ru.ifmo.cs.authorizator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

@SpringBootApplication(exclude = GsonAutoConfiguration.class)
public class AuthorizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizatorApplication.class, args);
    }

}
