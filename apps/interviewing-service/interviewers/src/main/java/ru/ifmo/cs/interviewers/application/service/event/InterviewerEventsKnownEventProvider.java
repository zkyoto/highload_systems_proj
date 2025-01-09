package ru.ifmo.cs.interviewers.application.service.event;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerActivatedEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerCreatedEvent;
import ru.ifmo.cs.interviewers.domain.event.InterviewerDemotedEvent;

@Service
public class InterviewerEventsKnownEventProvider implements KnownEventProvider {

    @Override
    public List<KnownDomainEvent> provide() {
        return List.of(new KnownDomainEvent(InterviewerActivatedEvent.EVENT_TYPE, InterviewerActivatedEvent.class),
                       new KnownDomainEvent(InterviewerCreatedEvent.EVENT_TYPE, InterviewerCreatedEvent.class),
                       new KnownDomainEvent(InterviewerDemotedEvent.EVENT_TYPE, InterviewerDemotedEvent.class));
    }

}
