package ru.ifmo.cs.interviews.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.interviews.presentation.controller.request_interceptor.InterviewsApiControllerServiceTokenValidator;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private static final String INTERVIEWS_API_PATTERN = "/api/v*/interviews/**";
    private final InterviewsApiControllerServiceTokenValidator interviewsApiControllerServiceTokenValidator;
    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator =
            new NotAllowedServiceTokenValidator();


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interviewsApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEWS_API_PATTERN);
        registry.addInterceptor(notAllowedServiceTokenValidator)
                .excludePathPatterns(INTERVIEWS_API_PATTERN);
    }

}
