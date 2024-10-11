package ru.itmo.cs.app.interviewing.candidate.domain.value;

import java.util.UUID;

public record CandidateId (UUID id) {
    public static CandidateId generate() {
        return new CandidateId(UUID.randomUUID());
    }

    public static CandidateId hydrate(String id) {
        return new CandidateId(UUID.fromString(id));
    }
}
