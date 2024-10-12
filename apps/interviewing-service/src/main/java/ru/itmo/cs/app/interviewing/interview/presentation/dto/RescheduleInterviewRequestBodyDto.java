package ru.itmo.cs.app.interviewing.interview.presentation.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record RescheduleInterviewRequestBodyDto(
        @JsonProperty("interview_id") InterviewId interviewId,
        @JsonProperty("new_scheduled_time") Instant newScheduledTime
) {}
