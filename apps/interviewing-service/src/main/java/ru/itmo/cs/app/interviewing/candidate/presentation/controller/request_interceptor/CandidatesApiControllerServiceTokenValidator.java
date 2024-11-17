package ru.itmo.cs.app.interviewing.candidate.presentation.controller.request_interceptor;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;

@Profile("candidate-web")
@Component
public class CandidatesApiControllerServiceTokenValidator extends AbstractServiceTokenValidator {
    public CandidatesApiControllerServiceTokenValidator(
            @Value("${security.candidates.service-id}") int serviceId,
            @Value("${security.candidates.allowed-service-ids}") int[] allowedServiceIdsForRequest,
            ServiceTokenResolver serviceTokenResolver
    ) {
        super(serviceId, IntStream.of(allowedServiceIdsForRequest).boxed().toList(), serviceTokenResolver);
    }
}
