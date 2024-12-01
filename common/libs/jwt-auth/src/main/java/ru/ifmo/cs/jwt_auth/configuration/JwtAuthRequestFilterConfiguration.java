package ru.ifmo.cs.jwt_auth.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.jwt_auth.infrastructure.request_filter.JwtTokenAuthenticationFilter;
import ru.ifmo.cs.passport.api.PassportFeignClient;

@EnableFeignClients(basePackageClasses = {ru.ifmo.cs.passport.api.PassportFeignClient.class})
@Configuration
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
