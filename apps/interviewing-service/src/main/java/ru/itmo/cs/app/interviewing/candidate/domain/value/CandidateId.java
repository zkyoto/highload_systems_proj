package ru.itmo.cs.app.interviewing.candidate.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

public record CandidateId (@JsonValue UUID value) {

    public static CandidateId generate() {
        return new CandidateId(UUID.randomUUID());
    }

    public static CandidateId hydrate(String uuid) {
        return new CandidateId(UUID.fromString(uuid));
    }

}
