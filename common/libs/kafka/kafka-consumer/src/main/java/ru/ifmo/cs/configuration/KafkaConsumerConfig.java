package ru.ifmo.cs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventFanoutDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka-topic.consume:}")
    private String[] topicsForConsume;
    @Value("${spring.kafka.consumer.group-id:}")
    private String kafkaConsumerGroupId;


    @Bean
    public KafkaEventsConsumer kafkaConsumer(
            IntegrationEventFanoutDelivererService integrationEventFanoutDelivererService,
            KnownIntegrationEventTypeResolver knownIntegrationEventTypeResolver,
            KafkaConsumerProperties kafkaConsumerProperties,
            ObjectMapper objectMapper
    ) {
        return new KafkaEventsConsumer(
                integrationEventFanoutDelivererService,
                knownIntegrationEventTypeResolver,
                kafkaConsumerProperties,
                objectMapper
        );
    }

    @Bean
    public KafkaConsumerProperties kafkaConsumerProperties() {
        return new KafkaConsumerProperties(topicsForConsume, kafkaConsumerGroupId);
    }

}
