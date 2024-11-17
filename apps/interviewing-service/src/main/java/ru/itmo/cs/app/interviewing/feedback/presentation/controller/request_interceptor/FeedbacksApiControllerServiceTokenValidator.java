package ru.itmo.cs.app.interviewing.feedback.presentation.controller.request_interceptor;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.service_token.application.AbstractServiceTokenValidator;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;

@Profile("feedback-web")
@Component
public class FeedbacksApiControllerServiceTokenValidator extends AbstractServiceTokenValidator {
    public FeedbacksApiControllerServiceTokenValidator(
            @Value("${security.feedbacks.service-id}") int serviceId,
            @Value("${security.feedbacks.allowed-service-ids}") int[] allowedServiceIdsForRequest,
            ServiceTokenResolver serviceTokenResolver
    ) {
        super(serviceId, IntStream.of(allowedServiceIdsForRequest).boxed().toList(), serviceTokenResolver);
    }
}
