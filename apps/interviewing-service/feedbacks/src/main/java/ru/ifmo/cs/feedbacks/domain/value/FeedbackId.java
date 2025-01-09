package ru.ifmo.cs.feedbacks.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record FeedbackId(@JsonValue UUID value) {

    public static FeedbackId generate() {
        return new FeedbackId(UUID.randomUUID());
    }

    @JsonCreator
    public static FeedbackId hydrate(String uuid) {
        return new FeedbackId(UUID.fromString(uuid));
    }

}
