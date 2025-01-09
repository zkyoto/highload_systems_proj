package ru.ifmo.cs.feedbacks.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.feedbacks.presentation.controller.request_interceptor.FeedbacksApiControllerServiceTokenValidator;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private static final String FEEDBACKS_API_PATTERN = "/api/v*/feedbacks/**";
    private final FeedbacksApiControllerServiceTokenValidator feedbacksApiControllerServiceTokenValidator;
    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator =
            new NotAllowedServiceTokenValidator();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(feedbacksApiControllerServiceTokenValidator)
                .addPathPatterns(FEEDBACKS_API_PATTERN);
        registry.addInterceptor(notAllowedServiceTokenValidator)
                .addPathPatterns(FEEDBACKS_API_PATTERN);
    }

}
