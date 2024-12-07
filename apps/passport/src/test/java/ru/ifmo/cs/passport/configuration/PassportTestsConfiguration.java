package ru.ifmo.cs.passport.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@TestConfiguration
public class PassportTestsConfiguration {
    @Primary
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        return Mockito.mock(R2dbcEntityTemplate.class);
    }
}
