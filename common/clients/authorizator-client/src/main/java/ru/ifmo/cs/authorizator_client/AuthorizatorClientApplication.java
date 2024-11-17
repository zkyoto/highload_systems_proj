package ru.ifmo.cs.authorizator_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AuthorizatorClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizatorClientApplication.class, args);
	}

}
