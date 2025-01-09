package ru.ifmo.cs.integration_event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ru.ifmo.cs.producer.KafkaProducer;

@AllArgsConstructor
public class KafkaIntegrationEventPublisher implements IntegrationEventPublisher {
    private final KafkaProducer producer;
    private final ObjectMapper mapper;

    @Override
    public void publish(IntegrationEvent integrationEvent) {
        String message;
        try {
            message = mapper.writeValueAsString(integrationEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        producer.write(integrationEvent.getDeduplicationKey(), message);
    }
}
