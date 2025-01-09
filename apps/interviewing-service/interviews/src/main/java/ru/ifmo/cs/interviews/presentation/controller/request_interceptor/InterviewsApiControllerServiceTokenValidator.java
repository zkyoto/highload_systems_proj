package ru.ifmo.cs.interviews.presentation.controller.request_interceptor;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;

@Component
public class InterviewsApiControllerServiceTokenValidator extends AbstractServiceTokenValidator {
    public InterviewsApiControllerServiceTokenValidator(
            @Value("${security.interviews.service-id}") int serviceId,
            @Value("${security.interviews.allowed-service-ids}") int[] allowedServiceIdsForRequest,
            ServiceTokenResolver serviceTokenResolver
    ) {
        super(serviceId, IntStream.of(allowedServiceIdsForRequest).boxed().toList(), serviceTokenResolver);
    }
}
