package ru.itmo.cs.app.interviewing.interview.presentation.controller.request_interceptor;

import java.util.Objects;
import java.util.stream.IntStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.domain.ServiceToken;

@Profile("interview-web")
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
