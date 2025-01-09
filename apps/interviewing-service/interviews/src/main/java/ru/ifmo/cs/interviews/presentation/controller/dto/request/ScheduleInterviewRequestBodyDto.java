package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleInterviewRequestBodyDto(
        @JsonProperty("interviewer_id") String interviewerId,
        @JsonProperty("candidate_id") String candidateId,
        @JsonProperty("scheduled_time") Instant scheduledTime
) {}
