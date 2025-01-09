package ru.ifmo.cs.consumer;

public record KafkaConsumerProperties(
        String[] topicsForConsume,
        String consumerGroupId
) {
}
