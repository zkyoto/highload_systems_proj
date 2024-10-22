package ru.itmo.cs.app.interviewing.candidate.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

public record CandidateProcessedEvent(
        CandidateId candidateId,
        Instant occurredOn
) implements CandidateEvent {
    public static final String EVENT_TYPE = "CandidateProcessedEvent";

    public static CandidateProcessedEvent fromEntity(Candidate candidate) {
        return new CandidateProcessedEvent(candidate.getId(), candidate.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

