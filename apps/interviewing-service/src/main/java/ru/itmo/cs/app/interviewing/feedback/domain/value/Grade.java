package ru.itmo.cs.app.interviewing.feedback.domain.value;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Grade {
    @JsonValue private final int value;

    public static Grade of(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Grade is null");
        }
        return of(value.intValue());
    }

    public static Grade of(int value) {
        if (value < 0 || value > 5) {
            throw new IllegalArgumentException("Grade must be between 0 and 5");
        }
        return new Grade(value);
    }

}
