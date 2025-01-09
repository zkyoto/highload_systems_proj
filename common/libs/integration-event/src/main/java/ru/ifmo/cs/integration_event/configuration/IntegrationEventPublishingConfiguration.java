package ru.ifmo.cs.integration_event.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ifmo.cs.integration_event.IntegrationEventPublisher;
import ru.ifmo.cs.integration_event.KafkaIntegrationEventPublisher;
import ru.ifmo.cs.integration_event.LoggableIntegrationEventPublisher;
import ru.ifmo.cs.producer.KafkaProducer;

@Configuration
public class IntegrationEventPublishingConfiguration {
    @Bean
    public KafkaIntegrationEventPublisher kafkaIntegrationEventPublisher(
            KafkaProducer kafkaProducer,
            ObjectMapper objectMapper
    ) {
        return new KafkaIntegrationEventPublisher(
                kafkaProducer, objectMapper
        );
    }

    @Primary
    @Bean
    public IntegrationEventPublisher integrationEventPublisher(
            KafkaIntegrationEventPublisher kafkaIntegrationEventPublisher
    ) {
        return new LoggableIntegrationEventPublisher(kafkaIntegrationEventPublisher);
    }
}
