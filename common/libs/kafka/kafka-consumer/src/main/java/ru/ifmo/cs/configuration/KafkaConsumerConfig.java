package ru.ifmo.cs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventFanoutDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public KafkaEventsConsumer kafkaConsumer(
            IntegrationEventFanoutDelivererService integrationEventFanoutDelivererService,
            KnownIntegrationEventTypeResolver knownIntegrationEventTypeResolver,
            ObjectMapper objectMapper
    ) {
        return new KafkaEventsConsumer(
                integrationEventFanoutDelivererService,
                knownIntegrationEventTypeResolver,
                objectMapper
        );
    }

}
