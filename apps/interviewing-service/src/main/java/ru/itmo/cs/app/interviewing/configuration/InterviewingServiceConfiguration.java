package ru.itmo.cs.app.interviewing.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.ifmo.cs.passport.api.config.StubPassportClientConfiguration;
import ru.ifmo.cs.service_token.configuration.TokenizerConfiguration;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.CandidatesServiceTokenValidatorsConfigurer;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.DefaultServiceTokenValidatorsConfigurer;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.FeedbacksServiceTokenValidatorsConfigurer;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.InterviewResultsServiceTokenValidatorsConfigurer;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.InterviewersServiceTokenValidatorsConfigurer;
import ru.itmo.cs.app.interviewing.configuration.service_token.validator.InterviewsServiceTokenValidatorsConfigurer;
import ru.itmo.cs.command_bus.configuration.CommandBusConfiguration;

@Configuration
@Import({
        StubPassportClientConfiguration.class,
        CommandBusConfiguration.class,
        DomainEventsConfiguration.class,
        TokenizerConfiguration.class,
        JsonMapperConfiguration.class,
        DefaultServiceTokenValidatorsConfigurer.class,
        CandidatesServiceTokenValidatorsConfigurer.class,
        FeedbacksServiceTokenValidatorsConfigurer.class,
        InterviewsServiceTokenValidatorsConfigurer.class,
        InterviewersServiceTokenValidatorsConfigurer.class,
        InterviewResultsServiceTokenValidatorsConfigurer.class,
})
public class InterviewingServiceConfiguration {
}
