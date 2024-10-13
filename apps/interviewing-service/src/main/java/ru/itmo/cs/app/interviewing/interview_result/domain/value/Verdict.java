package ru.itmo.cs.app.interviewing.interview_result.domain.value;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum Verdict implements StringEnum {
    HIRE("hire"),
    NO_HIRE("no_hire"),
    ;

    public static final StringEnumResolver<Verdict> R = StringEnumResolver.r(Verdict.class);

    @JsonValue private final String value;

    Verdict(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
