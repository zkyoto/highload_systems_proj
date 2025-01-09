package ru.ifmo.cs.feedbacks.domain.value;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    @JsonValue private final String value;

    public static Comment of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment value is null or empty");
        }
        return new Comment(value);
    }

}
