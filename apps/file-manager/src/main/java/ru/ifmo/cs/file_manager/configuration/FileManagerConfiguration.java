package ru.ifmo.cs.file_manager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itmo.cs.command_bus.configuration.CommandBusConfiguration;

@Configuration
@Import(CommandBusConfiguration.class)
public class FileManagerConfiguration {
}
