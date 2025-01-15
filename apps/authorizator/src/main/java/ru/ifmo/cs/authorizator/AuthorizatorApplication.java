package ru.ifmo.cs.authorizator;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackageClasses = ru.ifmo.cs.passport.api.PassportFeignClient.class)
@SpringBootApplication
public class AuthorizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizatorApplication.class, args);
    }

}
