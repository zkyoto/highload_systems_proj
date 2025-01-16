package ru.ifmo.cs.authorizator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackageClasses = ru.ifmo.cs.passport.api.PassportFeignClient.class)
@SpringBootApplication
public class AuthorizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizatorApplication.class, args);
    }

}
