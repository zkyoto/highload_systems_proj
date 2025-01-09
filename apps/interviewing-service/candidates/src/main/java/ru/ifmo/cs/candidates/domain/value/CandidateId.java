package ru.ifmo.cs.candidates.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record CandidateId (@JsonValue UUID value) {

    public static CandidateId generate() {
        return new CandidateId(UUID.randomUUID());
    }

    @JsonCreator
    public static CandidateId hydrate(String uuid) {
        return new CandidateId(UUID.fromString(uuid));
    }

}
