package ru.itmo.cs.app.interviewing.interviewer.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerStatus;

public record InterviewerResponseDto(
        @JsonProperty("interviewer_id") InterviewerId id,
        @JsonProperty("user_id") UserId userId,
        @JsonProperty("name") Name name,
        @JsonProperty("status") InterviewerStatus interviewerStatus
) {
    public static InterviewerResponseDto from(Interviewer interviewer) {
        return new InterviewerResponseDto(
                interviewer.getId(),
                interviewer.getUserId(),
                interviewer.getName(),
                interviewer.getInterviewerStatus()
        );
    }
}
