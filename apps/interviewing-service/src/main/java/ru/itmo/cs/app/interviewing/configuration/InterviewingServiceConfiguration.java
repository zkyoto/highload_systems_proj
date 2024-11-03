package ru.itmo.cs.app.interviewing.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.passport.api.config.StubPassportClientConfiguration;

@Configuration
@Import({
        StubPassportClientConfiguration.class,
        CommandBusConfiguration.class,
        DomainEventsConfiguration.class,
        TokenizerConfiguration.class,
        JsonMapperConfiguration.class,
        ServiceTokenValidatorsConfigurer.class,
})
public class InterviewingServiceConfiguration {
}
