package ru.itmo.cs.app.interviewing.interviewer.domain.value;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum InterviewerStatus implements StringEnum {
    ACTIVE("active"),
    DEMOTED("demoted"),
    PENDING_ACTIVATION("pending_activation"),
    ;

    @JsonValue private final String value;

    InterviewerStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    private static final StringEnumResolver<InterviewerStatus> R = StringEnumResolver.r(InterviewerStatus.class);
}
