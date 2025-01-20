package ru.ifmo.cs.synchronization.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class HazelCastConfiguration {

    @Bean
    public ClientConfig clientConfig() {
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress("hz-hazelcast");
        return config;
    }


    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    public IMap<String, LocalDateTime> timeMap(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("hazelcastTimeMap");
    }
}
