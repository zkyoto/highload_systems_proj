package ru.ifmo.cs.synchronization.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class HazelCastConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<String, LocalDateTime> timeMap(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("hazelcastTimeMap");
    }
}
