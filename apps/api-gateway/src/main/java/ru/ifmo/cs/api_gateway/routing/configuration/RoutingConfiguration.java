package ru.ifmo.cs.api_gateway.routing.configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
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
    private static final String AUTHORIZATOR_API_IRL = "lb://authorizator";
    private static final String INTERVIEW_API_URL = "lb://interviews";
    private static final String INTERVIEWER_API_URL = "lb://interviewers";
    private static final String INTERVIEW_RESULT_API_URL = "lb://interview-results";
    private static final String CANDIDATE_API_URL = "lb://candidates";
    private static final String FEEDBACK_API_URL = "lb://feedbacks";
    private static final String SERVICE_TOKEN_HEADER_KEY = "X-Service-Token";
    private static final String REQUEST_TIMESTAMP_HEADER_KEY = "X-Gateway-Timestamp";
    private static final String DESTINATION_SERVICE_API_DOCS_PATH = "/v3/api-docs";
    private final ServiceTokenResolver serviceTokenResolver;


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v*/interviews/**", "/api/v*/interviews")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(2)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(INTERVIEW_API_URL)
                )
                .route("swagger_interviews_exact_route",
                        r -> r
                                .path("/v3/api-docs/interviews")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interviews", DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(INTERVIEW_API_URL)
                )
                .route("swagger_interviewers_exact_route",
                        r -> r
                                .path("/v3/api-docs/interviewers")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interviewers", DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(INTERVIEWER_API_URL)
                )
                .route("swagger_interview-results_exact_route",
                        r -> r
                                .path("/v3/api-docs/interview-results")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/interview-results",
                                                DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(INTERVIEW_RESULT_API_URL)
                )
                .route("swagger_candidates_exact_route",
                        r -> r
                                .path("/v3/api-docs/candidates")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/candidates", DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(CANDIDATE_API_URL)
                )
                .route("swagger_feedbacks_exact_route",
                        r -> r
                                .path("/v3/api-docs/feedbacks")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/feedbacks", DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(FEEDBACK_API_URL)
                )
                .route("swagger_authorizator_exact_route",
                        r -> r
                                .path("/v3/api-docs/authorizator")
                                .filters(f -> f
                                        .rewritePath("/v3/api-docs/authorizator", DESTINATION_SERVICE_API_DOCS_PATH)
                                )
                                .uri(AUTHORIZATOR_API_IRL)
                )
                .route(p -> p
                        .path("/api/v*/interviewers/**", "/api/v*/interviewers")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(3)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(INTERVIEWER_API_URL)
                )
                .route(p -> p
                        .path("/api/v*/interview-results/**", "/api/v*/interview-results")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(4)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(INTERVIEW_RESULT_API_URL)
                )
                .route(p -> p
                        .path("/api/v*/candidates/**", "/api/v*/candidates")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(6)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(CANDIDATE_API_URL)
                )
                .route(p -> p
                        .path("/api/v*/feedbacks/**", "/api/v*/feedbacks")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(5)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(FEEDBACK_API_URL)
                )
                .route(p -> p
                        .path("/api/v*/auth/**")
                        .filters(f -> f
                                .addRequestHeader(
                                        SERVICE_TOKEN_HEADER_KEY,
                                        serviceTokenResolver.resolveServiceTokenFor(
                                                new RequestData(
                                                        new ServiceId(1),
                                                        new ServiceId(7)
                                                )
                                        ).value()
                                )
                                .addRequestHeader(
                                        REQUEST_TIMESTAMP_HEADER_KEY,
                                        DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                                )
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(AUTHORIZATOR_API_IRL)
                )
                .route(p -> p
                        .path("/availability")
                        .filters(f -> f
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(AUTHORIZATOR_API_IRL)
                )
                .route(p -> p
                        .path("/switch")
                        .filters(f -> f
                                .circuitBreaker(RoutingConfiguration::setUpDefaultCircuitBreaker)
                        )
                        .uri(AUTHORIZATOR_API_IRL)
                )
                .build();
    }

    private static void setUpDefaultCircuitBreaker(SpringCloudCircuitBreakerFilterFactory.Config c) {
        c.setName("exampleCircuitBreaker").setFallbackUri("forward:/fallback");
    }
}
