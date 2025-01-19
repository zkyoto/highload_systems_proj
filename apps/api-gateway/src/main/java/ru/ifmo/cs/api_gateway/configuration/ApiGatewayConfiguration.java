package ru.ifmo.cs.api_gateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.jwt_auth.configuration.JwtAuthRequestFilterConfiguration;

@Configuration
@Import({
        JwtAuthRequestFilterConfiguration.class,
})
public class ApiGatewayConfiguration {
}
