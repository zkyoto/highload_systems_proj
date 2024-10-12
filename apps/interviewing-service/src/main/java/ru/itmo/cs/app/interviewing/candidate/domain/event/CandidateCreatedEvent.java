package ru.itmo.cs.app.interviewing.candidate.domain.event;

import java.time.Instant;

import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

public record CandidateCreatedEvent(CandidateId id, Instant occurredOn) implements CandidateEvent {
    public static CandidateCreatedEvent fromEntity(Candidate candidate) {
        return new CandidateCreatedEvent(candidate.getId(), candidate.getCreatedAt());
    }

    @Override
    public String eventType() {
        return "CandidateCreatedEvent";
    }
}
