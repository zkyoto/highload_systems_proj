package ru.ifmo.cs.passport.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.ifmo.cs.passport.api.impl.StubPassportClient;

@Configuration
public class StubPassportClientConfiguration {
    @Bean
    public PassportClient passportClient() {
        return new StubPassportClient();
    }
}
