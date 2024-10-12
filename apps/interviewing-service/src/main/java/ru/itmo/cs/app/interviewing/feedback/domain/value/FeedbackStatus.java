package ru.itmo.cs.app.interviewing.feedback.domain.value;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum FeedbackStatus implements StringEnum {
    WAITING_FOR_SUBMIT("waiting_for_submit"),
    SUBMITTED("submitted"),
    ;

    public static final StringEnumResolver<FeedbackStatus> R = StringEnumResolver.r(FeedbackStatus.class);

    private final String value;

    FeedbackStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
