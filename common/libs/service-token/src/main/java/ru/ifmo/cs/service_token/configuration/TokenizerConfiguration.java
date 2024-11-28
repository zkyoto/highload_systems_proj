package ru.ifmo.cs.service_token.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.infrastructure.StubServiceTokenResolverImpl;

@Configuration
public class TokenizerConfiguration {
    @Bean
    public ServiceTokenResolver tokenizer() {
        return new StubServiceTokenResolverImpl();
    }
}
