package ru.ifmo.cs.passport.api.domain.value;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum Role implements StringEnum {
    SUPERVISOR("supervisor"),
    STAFF("staff"),
    INTERVIEWER("interviewer"),
    HR("hr"),
    OUT_OF_SCOPE("out-of-scope"),;

    public static final StringEnumResolver<Role> R = StringEnumResolver.r(Role.class);

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
