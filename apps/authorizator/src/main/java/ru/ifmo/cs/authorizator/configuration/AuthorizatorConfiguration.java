package ru.ifmo.cs.authorizator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ifmo.cs.jwt_auth.configuration.JwtAuthConfiguration;
import ru.ifmo.cs.passport.api.config.StubPassportClientConfiguration;
import ru.itmo.cs.command_bus.configuration.CommandBusConfiguration;

@Configuration
@Import({
        StubPassportClientConfiguration.class,
        JwtAuthConfiguration.class,
        CommandBusConfiguration.class,
})
public class AuthorizatorConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
