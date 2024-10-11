package ru.itmo.cs.app.interviewing.candidate.domain.value;

import ru.ifmo.cs.string_enum.StringEnum;
import ru.ifmo.cs.string_enum.StringEnumResolver;

public enum CandidateStatus implements StringEnum {
    WAITING_FOR_APPOINTMENT_AN_INTERVIEW("waiting_for_appointment_an_interview"),
    WAITING_FOR_INTERVIEW("waiting_for_interview");

    private final String value;

    public static final StringEnumResolver<CandidateStatus> R = StringEnumResolver.r(CandidateStatus.class);

    CandidateStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
