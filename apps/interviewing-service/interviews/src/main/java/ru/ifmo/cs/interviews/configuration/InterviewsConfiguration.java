package ru.ifmo.cs.interviews.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.configration.KafkaProducerConfig;
import ru.ifmo.cs.exception_handling.configration.ExceptionHandlingConfiguration;
import ru.ifmo.cs.service_token.configuration.TokenizerConfiguration;
import ru.itmo.cs.command_bus.configuration.CommandBusConfiguration;

@Configuration
@Import({
        CommandBusConfiguration.class,
        TokenizerConfiguration.class,
        JsonMapperConfiguration.class,
        EventsConfiguration.class,
        ServiceTokenValidatorsConfigurer.class,
        KafkaProducerConfig.class,
        ExceptionHandlingConfiguration.class,
})
public class InterviewsConfiguration {
}
