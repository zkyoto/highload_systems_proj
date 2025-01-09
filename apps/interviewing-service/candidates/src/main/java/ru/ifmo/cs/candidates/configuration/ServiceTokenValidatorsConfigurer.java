package ru.ifmo.cs.candidates.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.candidates.presentation.controller.request_interceptor.CandidatesApiControllerServiceTokenValidator;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private static final String CANDIDATES_API_PATTERN = "/api/v*/candidates/**";
    private final CandidatesApiControllerServiceTokenValidator candidatesApiControllerServiceTokenValidator;
    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator =
            new NotAllowedServiceTokenValidator();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(candidatesApiControllerServiceTokenValidator)
                .addPathPatterns(CANDIDATES_API_PATTERN);
        registry.addInterceptor(notAllowedServiceTokenValidator)
                .addPathPatterns(CANDIDATES_API_PATTERN);
    }

}
