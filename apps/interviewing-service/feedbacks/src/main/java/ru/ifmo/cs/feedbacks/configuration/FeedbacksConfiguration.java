package ru.ifmo.cs.feedbacks.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.configuration.KafkaConsumerConfig;
import ru.ifmo.cs.service_token.configuration.TokenizerConfiguration;
import ru.itmo.cs.command_bus.configuration.CommandBusConfiguration;

@Configuration
@Import({
        CommandBusConfiguration.class,
        TokenizerConfiguration.class,
        JsonMapperConfiguration.class,
        EventsConfiguration.class,
        ServiceTokenValidatorsConfigurer.class,
        KafkaConsumerConfig.class,
})
public class FeedbacksConfiguration {
}
