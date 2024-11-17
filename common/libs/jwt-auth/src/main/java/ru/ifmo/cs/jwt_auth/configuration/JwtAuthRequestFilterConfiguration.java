package ru.ifmo.cs.jwt_auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.jwt_auth.infrastructure.jwt.JwtImpl;
import ru.ifmo.cs.jwt_auth.infrastructure.request_filter.JwtTokenAuthenticationFilter;
import ru.ifmo.cs.passport.api.PassportClient;

@Configuration
public class JwtAuthRequestFilterConfiguration {

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(
            JwtResolver jwtResolver,
            JwtValidator jwtValidator,
            PassportClient passportClient
    ) {
        return new JwtTokenAuthenticationFilter(jwtResolver, jwtValidator, passportClient);
    }
}
