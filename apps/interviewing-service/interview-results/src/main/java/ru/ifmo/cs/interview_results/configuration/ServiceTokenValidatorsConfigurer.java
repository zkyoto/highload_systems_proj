package ru.ifmo.cs.interview_results.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.interview_results.presentation.controller.request_interceptor.InterviewResultsApiControllerServiceTokenValidator;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private static final String INTERVIEW_RESULTS_API_PATTERN = "/api/v*/interview-results/**";
    private final InterviewResultsApiControllerServiceTokenValidator interviewResultsApiControllerServiceTokenValidator;
    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator =
            new NotAllowedServiceTokenValidator();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interviewResultsApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEW_RESULTS_API_PATTERN);
        registry.addInterceptor(notAllowedServiceTokenValidator)
                .addPathPatterns(INTERVIEW_RESULTS_API_PATTERN);
    }

}
