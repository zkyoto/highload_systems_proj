package ru.ifmo.cs.interviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { GsonAutoConfiguration.class
, JacksonAutoConfiguration.class})
@EnableScheduling
@EnableFeignClients(basePackageClasses = ru.ifmo.cs.passport.api.PassportFeignClient.class)
public class InterviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewsApplication.class, args);
    }

}
