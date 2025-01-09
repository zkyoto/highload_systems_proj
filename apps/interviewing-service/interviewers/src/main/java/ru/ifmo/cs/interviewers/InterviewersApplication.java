package ru.ifmo.cs.interviewers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = GsonAutoConfiguration.class)
@EnableScheduling
@EnableFeignClients(basePackageClasses = ru.ifmo.cs.passport.api.PassportFeignClient.class)
public class InterviewersApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewersApplication.class, args);
    }

}
