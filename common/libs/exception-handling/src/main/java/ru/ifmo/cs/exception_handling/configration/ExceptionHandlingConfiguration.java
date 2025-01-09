package ru.ifmo.cs.exception_handling.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.cs.exception_handling.ExceptionHandlingController;

@Configuration
public class ExceptionHandlingConfiguration {
    @Bean
    public ExceptionHandlingController exceptionHandler() {
        return new ExceptionHandlingController();
    }
}
