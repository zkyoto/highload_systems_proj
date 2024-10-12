package ru.itmo.cs.app.interviewing.feedback.domain.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private final String value;

    public static Comment of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment value is null or empty");
        }
        return new Comment(value);
    }

}
