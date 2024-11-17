package ru.ifmo.cs.api_gateway.routing.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RoutingConfiguration {
    private final String authorizatorUrl = "lb://authorizator";

    @Bean
    public RouteLocator defaultRoutes(RouteLocatorBuilder builder) {
        return builder.routes().build();
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/test")
                        .filters(f -> f
//                                .addRequestHeader(
//                                        "X-Gateway-Timestamp",
//                                        DateTimeFormatter.ISO_DATE_TIME.format(Instant.now())
//                                )
                                .circuitBreaker(c -> c
                                        .setName("exampleCircuitBreaker")
                                        .setFallbackUri("forward:/fallback"))
                        )
                        .uri(authorizatorUrl))
                .build();
    }

//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("example_route", r -> r
//                        .path("/example/**")
//                        .filters(f -> f
//                                .circuitBreaker(c -> c
//                                        .setName("exampleCircuitBreaker")
//                                        .setFallbackUri("forward:/fallback")))
//                        .uri("http://localhost:8081"))
//                .build();
//    }
}
