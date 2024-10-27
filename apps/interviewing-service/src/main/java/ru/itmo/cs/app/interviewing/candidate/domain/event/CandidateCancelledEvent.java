package ru.itmo.cs.app.interviewing.candidate.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;

public record CandidateCancelledEvent(
        @JsonProperty("candidateId") CandidateId candidateId,
        @JsonProperty("occurredOn") Instant occurredOn
) implements CandidateEvent {
    public static final String EVENT_TYPE = "CandidateCancelledEvent";

    public static CandidateCancelledEvent fromEntity(Candidate candidate) {
        return new CandidateCancelledEvent(candidate.getId(), candidate.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}

