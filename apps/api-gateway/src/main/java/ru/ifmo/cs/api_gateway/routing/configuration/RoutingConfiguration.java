package ru.ifmo.cs.api_gateway.routing.configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.service_token.application.ServiceTokenResolver;
import ru.ifmo.cs.service_token.configuration.TokenizerConfiguration;
import ru.ifmo.cs.service_token.domain.RequestData;
import ru.ifmo.cs.service_token.domain.ServiceId;


@Configuration
@Import(TokenizerConfiguration.class)
@AllArgsConstructor
public class RoutingConfiguration {
    private static final String authorizatorServiceUrl = "lb://authorizator";
    private static final String interviewApiServiceUrl = "lb://interviews";
    private static final String interviewerApiServiceUrl = "lb://interviewers";
    private static final String interviewResultApiServiceUrl = "lb://interview-results";
    private static final String candidateApiServiceUrl = "lb://candidates";
    private static final String feedbackApiServiceUrl = "lb://feedbacks";
    private final ServiceTokenResolver serviceTokenResolver;


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
                        )
                        .uri(interviewApiServiceUrl)
                )
                .route("swagger_interviews_exact_route",
                        r -> r
                                .path("/v3/api-docs/interviews")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interviews", "/v3/api-docs")
                                )
                                .uri(interviewApiServiceUrl)
                )
                .route("swagger_interviewers_exact_route",
                        r -> r
                                .path("/v3/api-docs/interviewers")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interviewers", "/v3/api-docs")
                                )
                                .uri(interviewerApiServiceUrl)
                )
                .route("swagger_interview-results_exact_route",
                        r -> r
                                .path("/v3/api-docs/interview-results")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interview-results", "/v3/api-docs")
                                )
                                .uri(interviewResultApiServiceUrl)
                )
                .route("swagger_candidates_exact_route",
                        r -> r
                                .path("/v3/api-docs/candidates")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/candidates", "/v3/api-docs")
                                )
                                .uri(candidateApiServiceUrl)
                )
                .route("swagger_feedbacks_exact_route",
                        r -> r
                                .path("/v3/api-docs/feedbacks")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/feedbacks", "/v3/api-docs")
                                )
                                .uri(feedbackApiServiceUrl)
                )
                .route("swagger_authorizator_exact_route",
                        r -> r
                                .path("/v3/api-docs/authorizator")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/authorizator", "/v3/api-docs")
                                )
                                .uri(authorizatorServiceUrl)
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
                        )
                        .uri(authorizatorServiceUrl)
                )
                .build();
    }
}
