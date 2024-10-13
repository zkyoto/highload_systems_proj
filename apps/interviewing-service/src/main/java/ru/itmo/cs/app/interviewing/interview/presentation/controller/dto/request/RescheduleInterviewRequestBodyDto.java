package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record RescheduleInterviewRequestBodyDto(
        @JsonProperty("interview_id") String interviewId,
        @JsonProperty("new_scheduled_time") Instant newScheduledTime
) {}
