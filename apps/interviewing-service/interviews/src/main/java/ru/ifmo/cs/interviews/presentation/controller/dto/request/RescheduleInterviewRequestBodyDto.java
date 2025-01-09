package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RescheduleInterviewRequestBodyDto(
        @JsonProperty("interview_id") String interviewId,
        @JsonProperty("new_scheduled_time") Instant newScheduledTime
) {}
