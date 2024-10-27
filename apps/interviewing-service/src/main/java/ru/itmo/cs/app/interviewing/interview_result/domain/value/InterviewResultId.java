package ru.itmo.cs.app.interviewing.interview_result.domain.value;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record InterviewResultId(@JsonValue UUID value) {

    public static InterviewResultId generate(){
        return new InterviewResultId(UUID.randomUUID());
    }

    @JsonCreator
    public static InterviewResultId hydrate(String uuid) {
        return new InterviewResultId(UUID.fromString(uuid));
    }

}
