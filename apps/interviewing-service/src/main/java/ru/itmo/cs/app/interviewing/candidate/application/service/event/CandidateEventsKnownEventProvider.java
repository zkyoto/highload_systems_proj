package ru.itmo.cs.app.interviewing.candidate.application.service.event;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.domain_event.application.service.KnownEventProvider;
import ru.ifmo.cs.domain_event.domain.KnownDomainEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateCreatedEvent;
import ru.itmo.cs.app.interviewing.candidate.domain.event.CandidateScheduledForInterviewEvent;

@Service
public class CandidateEventsKnownEventProvider implements KnownEventProvider {

    @Override
    public List<KnownDomainEvent> provide() {
        return List.of(new KnownDomainEvent(CandidateCreatedEvent.EVENT_TYPE, CandidateCreatedEvent.class),
                       new KnownDomainEvent(CandidateScheduledForInterviewEvent.EVENT_TYPE, CandidateCreatedEvent.class));
    }

}
