package ru.ifmo.cs.interviewers.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.interviewers.presentation.controller.request_interceptor.InterviewersApiControllerServiceTokenValidator;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private static final String INTERVIEWERS_API_PATTERN = "/api/v*/interviewers/**";
    private final InterviewersApiControllerServiceTokenValidator interviewersApiControllerServiceTokenValidator;
    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator =
            new NotAllowedServiceTokenValidator();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interviewersApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEWERS_API_PATTERN);
        registry.addInterceptor(notAllowedServiceTokenValidator)
                .addPathPatterns(INTERVIEWERS_API_PATTERN);
    }

}
