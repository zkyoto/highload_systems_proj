package ru.itmo.cs.app.interviewing.interview.domain.value;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum ScheduleStatus implements StringEnum {
    ACTUAL("actual"),
    CANCELLED("cancelled");

    private final String value;

    public static final StringEnumResolver<ScheduleStatus> R = StringEnumResolver.r(ScheduleStatus.class);

    ScheduleStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
