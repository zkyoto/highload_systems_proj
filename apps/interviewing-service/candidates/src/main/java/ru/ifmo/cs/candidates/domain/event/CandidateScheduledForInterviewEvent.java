package ru.ifmo.cs.candidates.domain.event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.value.CandidateId;

public record CandidateScheduledForInterviewEvent(
        @JsonProperty("candidateId") CandidateId candidateId,
        @JsonProperty("occurredOn") Instant occurredOn
) implements CandidateEvent {
    public static final String EVENT_TYPE = "CandidateScheduledForInterviewEvent";

    public static CandidateScheduledForInterviewEvent fromEntity(Candidate candidate) {
        return new CandidateScheduledForInterviewEvent(candidate.getId(), candidate.getCreatedAt());
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }
}
