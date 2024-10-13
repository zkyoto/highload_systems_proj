package ru.itmo.cs.app.interviewing.interview.application.service.event;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewCancelledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewRescheduledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;

@Service
public class InterviewEventsKnownEventProvider implements KnownEventProvider {

    @Override
    public List<KnownDomainEvent> provide() {
        return List.of(new KnownDomainEvent(InterviewScheduledEvent.EVENT_TYPE, InterviewScheduledEvent.class),
                       new KnownDomainEvent(InterviewRescheduledEvent.EVENT_TYPE, InterviewRescheduledEvent.class),
                       new KnownDomainEvent(InterviewCancelledEvent.EVENT_TYPE, InterviewCancelledEvent.class));
    }

}
