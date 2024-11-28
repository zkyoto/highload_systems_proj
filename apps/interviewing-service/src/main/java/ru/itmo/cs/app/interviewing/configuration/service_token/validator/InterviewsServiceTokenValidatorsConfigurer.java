package ru.itmo.cs.app.interviewing.configuration.service_token.validator;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;
import ru.itmo.cs.app.interviewing.candidate.presentation.controller.request_interceptor.CandidatesApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.request_interceptor.FeedbacksApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.request_interceptor.InterviewsApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.request_interceptor.InterviewResultsApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.request_interceptor.InterviewersApiControllerServiceTokenValidator;

@Profile("interview-web")
@Configuration
@AllArgsConstructor
public class InterviewsServiceTokenValidatorsConfigurer extends AbstractServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private final InterviewsApiControllerServiceTokenValidator interviewsApiControllerServiceTokenValidator;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interviewsApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEWS_API_PATTERN);
    }

}
