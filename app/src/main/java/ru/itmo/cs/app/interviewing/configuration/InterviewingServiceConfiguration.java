package ru.itmo.cs.app.interviewing.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.cs.common.command_bus.impl.SimpleCommandBusService;
import ru.itmo.cs.common.command_bus.impl.SpringContextHandlerResolver;
import ru.itmo.cs.common.command_bus.impl.SpringContextSimpleCommandBusProcessor;

@Configuration
public class InterviewingServiceConfiguration {

    @Bean
    public SimpleCommandBusService commandBusService(
            SpringContextHandlerResolver springContextHandlerResolver
    ) {
        return new SimpleCommandBusService(new SpringContextSimpleCommandBusProcessor(
                springContextHandlerResolver
        ));
    }

    @Bean
    SpringContextHandlerResolver springContextHandlerResolver(ApplicationContext applicationContext) {
        return new SpringContextHandlerResolver(applicationContext);
    }
}
