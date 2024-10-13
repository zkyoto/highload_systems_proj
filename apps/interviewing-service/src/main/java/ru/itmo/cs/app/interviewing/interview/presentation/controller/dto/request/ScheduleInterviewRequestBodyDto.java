package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record ScheduleInterviewRequestBodyDto(
        @JsonProperty("interviewer_id") String interviewerId,
        @JsonProperty("candidate_id") String candidateId,
        @JsonProperty("scheduled_time") Instant scheduledTime
) {}
