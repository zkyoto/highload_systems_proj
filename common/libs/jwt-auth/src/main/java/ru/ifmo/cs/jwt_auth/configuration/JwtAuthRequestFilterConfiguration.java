package ru.ifmo.cs.jwt_auth.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.jwt_auth.infrastructure.request_filter.JwtTokenAuthenticationFilter;
import ru.ifmo.cs.jwt_token.application.JwtResolver;
import ru.ifmo.cs.jwt_token.application.JwtValidator;
import ru.ifmo.cs.configuration.JwtAuthServiceConfiguration;
import ru.ifmo.cs.passport.api.PassportFeignClient;

@EnableFeignClients(basePackageClasses = {ru.ifmo.cs.passport.api.PassportFeignClient.class})
@Configuration
@Import(JwtAuthServiceConfiguration.class)
public class JwtAuthRequestFilterConfiguration {

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(
            JwtResolver jwtResolver,
            JwtValidator jwtValidator,
            PassportFeignClient passportClient
    ) {
        return new JwtTokenAuthenticationFilter(jwtResolver, jwtValidator, passportClient);
    }
}
