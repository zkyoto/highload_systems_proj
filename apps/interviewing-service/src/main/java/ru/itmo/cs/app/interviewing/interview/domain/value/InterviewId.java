package ru.itmo.cs.app.interviewing.interview.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record InterviewId(@JsonValue UUID value) {
    @JsonCreator
    public static InterviewId hydrate(String uuid) {
        return new InterviewId(UUID.fromString(uuid));
    }

    public static InterviewId generate() {
        return new InterviewId(UUID.randomUUID());
    }

}
