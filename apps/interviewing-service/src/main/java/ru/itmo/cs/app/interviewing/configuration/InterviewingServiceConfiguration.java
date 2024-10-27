package ru.itmo.cs.app.interviewing.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.passport.api.config.StubPassportClientConfiguration;
import ru.itmo.cs.command_bus.impl.SimpleCommandBusService;
import ru.itmo.cs.command_bus.impl.SpringContextHandlerResolver;
import ru.itmo.cs.command_bus.impl.SpringContextSimpleCommandBusProcessor;

@Configuration
@Import({
        StubPassportClientConfiguration.class,
        CommandBusConfiguration.class,
        CronTasksConfiguration.class,
        DomainEventsConfiguration.class,
})
public class InterviewingServiceConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
