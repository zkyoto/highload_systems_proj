package ru.itmo.cs.app.interviewing.feedback.domain.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Grade {
    private final int value;

    public static Grade of(int value) {
        if (value < 0 || value > 5) {
            throw new IllegalArgumentException("Grade must be between 0 and 5");
        }
        return new Grade(value);
    }

}
