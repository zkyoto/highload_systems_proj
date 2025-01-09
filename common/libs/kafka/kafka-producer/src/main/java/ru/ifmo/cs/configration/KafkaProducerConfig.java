package ru.ifmo.cs.configration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import ru.ifmo.cs.producer.KafkaProducer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
    @Value("${kafka-topic.produce}")
    String topicName;

    @Bean
    public KafkaProducer kafkaProducer(
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        return new KafkaProducer(kafkaTemplate, topicName);
    }

}
