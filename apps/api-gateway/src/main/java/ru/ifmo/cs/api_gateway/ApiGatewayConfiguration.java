package ru.ifmo.cs.api_gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.jwt_auth.configuration.JwtAuthConfiguration;
import ru.ifmo.cs.passport.api.config.StubPassportClientConfiguration;

@Configuration
@Import({
        StubPassportClientConfiguration.class,
        JwtAuthConfiguration.class
})
public class ApiGatewayConfiguration {
}
