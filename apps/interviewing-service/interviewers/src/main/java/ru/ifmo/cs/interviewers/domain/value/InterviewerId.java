package ru.ifmo.cs.interviewers.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record InterviewerId(@JsonValue UUID value) {

    public static InterviewerId generate() {
        return new InterviewerId(UUID.randomUUID());
    }

    @JsonCreator
    public static InterviewerId hydrate(String uuid) {
        return new InterviewerId(UUID.fromString(uuid));
    }

}
