package ru.ifmo.cs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import ru.ifmo.cs.consumer.KafkaEventsConsumer;
import ru.ifmo.cs.consumer.KafkaConsumerProperties;
import ru.ifmo.cs.integration_event.event_delivery.IntegrationEventFanoutDelivererService;
import ru.ifmo.cs.integration_event.event_delivery.KnownIntegrationEventTypeResolver;

@EnableKafka
@Configuration
public class KafkaIntegrationEventConsumerConfig {
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

//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
//    kafkaManualAckListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(configuredConsumerFactory("clientIdViaProps3"));
//        ContainerProperties props = factory.getContainerProperties();
//        props.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
//        props.setIdleEventInterval(100L);
//        props.setPollTimeout(50L);
//        factory.setRecordFilterStrategy(manualFilter());
//        factory.setAckDiscarded(true);
//        return factory;
//    }
//
}
