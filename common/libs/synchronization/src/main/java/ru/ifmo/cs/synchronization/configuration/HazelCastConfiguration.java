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

    private static final String HAZELCAST_SERVICE_NAME = "hz-hazelcast";

    @Bean
    public HazelcastInstance hazelcastInstance() {
        final ClientConfig config = new ClientConfig();
        config.setClusterName("dev");

        config.getNetworkConfig().getKubernetesConfig()
                .setEnabled(true)
                .setProperty("namespace", "default")
                .setProperty("service-name", HAZELCAST_SERVICE_NAME);

        return HazelcastClient.newHazelcastClient(config);
    }


    @Bean
    public IMap<String, LocalDateTime> timeMap(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("hazelcastTimeMap");
    }
}
