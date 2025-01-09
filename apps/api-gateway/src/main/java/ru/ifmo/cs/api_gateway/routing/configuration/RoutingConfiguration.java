package ru.ifmo.cs.api_gateway.routing.configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.api_gateway.tracing.RequestInterceptor;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.configuration.TokenizerConfiguration;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;


@Configuration
@Import(TokenizerConfiguration.class)
@AllArgsConstructor
public class RoutingConfiguration {
    private static final String authorizatorServiceUrl = "lb://authorizator";
    private static final String interviewApiServiceUrl = "lb://interviewing-service.interview-web";
    private static final String interviewerApiServiceUrl = "lb://interviewing-service.interviewer-web";
    private static final String interviewResultApiServiceUrl = "lb://interviewing-service.interview-result-web";
    private static final String candidateApiServiceUrl = "lb://interviewing-service.candidate-web";
    private static final String feedbackApiServiceUrl = "lb://interviewing-service.feedback-web";
    private final ServiceTokenResolver serviceTokenResolver;
    private final RequestInterceptor requestInterceptor;


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v*/interviews/**", "/api/v*/interviews")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(2)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(interviewApiServiceUrl)
                )
                .route(p -> p
                        .path("/api/v*/interviewers/**", "/api/v*/interviewers")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(3)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(interviewerApiServiceUrl)
                )
                .route(p -> p
                        .path("/api/v*/interview-results/**", "/api/v*/interview-results")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(4)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(interviewResultApiServiceUrl)
                )
                .route(p -> p
                        .path("/api/v*/candidates/**", "/api/v*/candidates")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(6)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(candidateApiServiceUrl)
                )
                .route(p -> p
                        .path("/api/v*/feedbacks/**", "/api/v*/feedbacks")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(5)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(feedbackApiServiceUrl)
                )
                .route(p -> p
                        .path("/api/v*/auth/**")
                        .filters(f -> f
                                .addRequestHeader(
                                        "X-Service-Token",
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(7)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        "X-Gateway-Timestamp",
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(requestInterceptor)
                        )
                        .uri(authorizatorServiceUrl)
                )
                .build();
    }
}
