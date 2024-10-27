package ru.itmo.cs.app.interviewing.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.itmo.cs.app.interviewing.candidate.presentation.consumer.InterviewCancelledEventCandidateDomainEventConsumer;
import ru.itmo.cs.app.interviewing.candidate.presentation.consumer.InterviewResultCreatedEventCandidateDomainEventConsumer;
import ru.itmo.cs.app.interviewing.candidate.presentation.consumer.InterviewScheduledEventCandidateDomainEventConsumer;
import ru.itmo.cs.app.interviewing.feedback.presentation.consumer.InterviewScheduledEventFeedbackDomainEventConsumer;

@TestConfiguration
public class TurnOffAllDomainEventConsumers {

    @Primary
    @Bean
    public InterviewScheduledEventFeedbackDomainEventConsumer interviewScheduledEventFeedbackDomainEventConsumer() {
        return Mockito.mock(InterviewScheduledEventFeedbackDomainEventConsumer.class);
    }

    @Primary
    @Bean
    public InterviewCancelledEventCandidateDomainEventConsumer interviewCancelledEventCandidateDomainEventConsumer() {
        return Mockito.mock(InterviewCancelledEventCandidateDomainEventConsumer.class);
    }

    @Primary
    @Bean
    public InterviewResultCreatedEventCandidateDomainEventConsumer interviewResultCreatedEventCandidateDomainEventConsumer() {
        return Mockito.mock(InterviewResultCreatedEventCandidateDomainEventConsumer.class);
    }

    @Primary
    @Bean
    public InterviewScheduledEventCandidateDomainEventConsumer interviewScheduledEventCandidateDomainEventConsumer() {
        return Mockito.mock(InterviewScheduledEventCandidateDomainEventConsumer.class);
    }

}
