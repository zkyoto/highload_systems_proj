package ru.ifmo.cs.candidates.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.jwt_token.configuration.KafkaConsumerConfig;
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
        ExceptionHandlingConfiguration.class,
        KafkaConsumerConfig.class,
})
public class CandidatesConfiguration {
}
