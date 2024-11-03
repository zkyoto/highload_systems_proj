package ru.itmo.cs.app.interviewing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.infrastructure.StubServiceTokenResolverImpl;

@Component
public class TokenizerConfiguration {
    @Bean
    public ServiceTokenResolver tokenizer() {
        return new StubServiceTokenResolverImpl();
    }
}
