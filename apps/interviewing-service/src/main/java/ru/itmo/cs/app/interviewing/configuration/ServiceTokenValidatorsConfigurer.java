package ru.itmo.cs.app.interviewing.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ifmo.cs.service_token.infrastructure.NotAllowedServiceTokenValidator;
import ru.itmo.cs.app.interviewing.candidate.presentation.controller.request_interceptor.CandidatesApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.request_interceptor.FeedbacksApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.request_interceptor.InterviewsApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.request_interceptor.InterviewResultsApiControllerServiceTokenValidator;
import ru.itmo.cs.app.interviewing.interviewer.presentation.controller.request_interceptor.InterviewersApiControllerServiceTokenValidator;

@Configuration
@AllArgsConstructor
public class ServiceTokenValidatorsConfigurer implements WebMvcConfigurer {
    private final InterviewersApiControllerServiceTokenValidator interviewersApiControllerServiceTokenValidator;
    private static final String INTERVIEWERS_API_PATTERN = "/api/v*/interviewers/**";

    private final InterviewsApiControllerServiceTokenValidator interviewsApiControllerServiceTokenValidator;
    private static final String INTERVIEWS_API_PATTERN = "/api/v*/interviews/**";

    private final InterviewResultsApiControllerServiceTokenValidator interviewResultsApiControllerServiceTokenValidator;
    private static final String INTERVIEW_RESULTS_API_PATTERN = "/api/v*/interview-results/**";

    private final FeedbacksApiControllerServiceTokenValidator feedbacksApiControllerServiceTokenValidator;
    private static final String FEEDBACKS_API_PATTERN = "/api/v*/feedbacks/**";

    private final CandidatesApiControllerServiceTokenValidator candidatesApiControllerServiceTokenValidator;
    private static final String CANDIDATES_API_PATTERN = "/api/v*/candidates/**";

    private final NotAllowedServiceTokenValidator notAllowedServiceTokenValidator = new NotAllowedServiceTokenValidator();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interviewersApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEWERS_API_PATTERN);
        registry.addInterceptor(interviewResultsApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEW_RESULTS_API_PATTERN);
        registry.addInterceptor(interviewsApiControllerServiceTokenValidator)
                .addPathPatterns(INTERVIEWS_API_PATTERN);
        registry.addInterceptor(feedbacksApiControllerServiceTokenValidator)
                .addPathPatterns(FEEDBACKS_API_PATTERN);
        registry.addInterceptor(candidatesApiControllerServiceTokenValidator)
                .addPathPatterns(CANDIDATES_API_PATTERN);

        registry.addInterceptor(notAllowedServiceTokenValidator)
                .addPathPatterns("/**")
                .excludePathPatterns(INTERVIEWERS_API_PATTERN)
                .excludePathPatterns(INTERVIEW_RESULTS_API_PATTERN)
                .excludePathPatterns(FEEDBACKS_API_PATTERN)
                .excludePathPatterns(CANDIDATES_API_PATTERN)
                .excludePathPatterns(INTERVIEWS_API_PATTERN);
    }

}
