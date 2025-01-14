package ru.ifmo.cs.interviewers.presentation.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;
import ru.ifmo.cs.interviewers.domain.value.InterviewerStatus;

public record InterviewerResponseDto(

        @JsonProperty("interviewer_id")
        @Schema(description = "Unique identifier of the interviewer as a UUID", example = "123e4567-e89b-12d3-a456-426614174000")
        InterviewerId id,

        @JsonProperty("user_id")
        @Schema(description = "Unique identifier of the user", example = "12345")
        UserId userId,

        @JsonProperty("name")
        @Schema(description = "Name of the interviewer", example = "John Doe")
        Name name,

        @JsonProperty("status")
        @Schema(description = "Current status of the interviewer", example = "ACTIVE")
        InterviewerStatus interviewerStatus

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