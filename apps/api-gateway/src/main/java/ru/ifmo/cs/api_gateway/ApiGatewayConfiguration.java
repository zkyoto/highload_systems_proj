package ru.ifmo.cs.api_gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.api_gateway.routing.configuration.RoutingConfiguration;
import ru.ifmo.cs.jwt_auth.configuration.JwtAuthRequestFilterConfiguration;

@Configuration
@Import({
        JwtAuthRequestFilterConfiguration.class,
        RoutingConfiguration.class,
})
public class ApiGatewayConfiguration {
}
