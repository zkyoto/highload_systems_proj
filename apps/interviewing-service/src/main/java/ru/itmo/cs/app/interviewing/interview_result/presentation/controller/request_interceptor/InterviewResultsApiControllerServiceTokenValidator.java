package ru.itmo.cs.app.interviewing.interview_result.presentation.controller.request_interceptor;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;

@Profile("interview-result-web")
@Component
public class InterviewResultsApiControllerServiceTokenValidator extends AbstractServiceTokenValidator {
    public InterviewResultsApiControllerServiceTokenValidator(
            @Value("${security.interview-results.service-id}") int serviceId,
            @Value("${security.interview-results.allowed-service-ids}") int[] allowedServiceIdsForRequest,
            ServiceTokenResolver serviceTokenResolver
    ) {
        super(serviceId, IntStream.of(allowedServiceIdsForRequest).boxed().toList(), serviceTokenResolver);
    }
}
