package ru.itmo.cs.app.interviewing.candidate.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

public record CandidateCreatedEvent(
        @JsonProperty("candidateId") CandidateId id,
        @JsonProperty("occurredOn") Instant occurredOn) implements CandidateEvent {
    public static final String EVENT_TYPE = "CandidateCreatedEvent";

    public static CandidateCreatedEvent fromEntity(Candidate candidate) {
        return new CandidateCreatedEvent(candidate.getId(), candidate.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
