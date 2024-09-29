package ru.itmo.cs.app.interviewing.interviewer.domain.value;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum InterviewerStatus implements StringEnum {
    ACTIVE("active"),
    DEMOTED("demoted"),
    PENDING_ACTIVATION("pending_activation");

    private final String value;

    InterviewerStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    private static final StringEnumResolver<InterviewerStatus> R = StringEnumResolver.r(InterviewerStatus.class);
}
