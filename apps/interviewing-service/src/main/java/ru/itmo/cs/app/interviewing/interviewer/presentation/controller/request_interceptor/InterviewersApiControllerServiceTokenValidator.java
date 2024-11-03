package ru.itmo.cs.app.interviewing.interviewer.presentation.controller.request_interceptor;

import java.util.stream.IntStream;

import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InterviewersApiControllerServiceTokenValidator extends AbstractServiceTokenValidator {

    public InterviewersApiControllerServiceTokenValidator(
            @Value("${security.interviewers.service-id}") int serviceId,
            @Value("${security.interviewers.allowed-service-ids}") int[] allowedServiceIdsForRequest,
            ServiceTokenResolver serviceTokenResolver
    ) {
        super(serviceId, IntStream.of(allowedServiceIdsForRequest).boxed().toList(), serviceTokenResolver);
    }

}
