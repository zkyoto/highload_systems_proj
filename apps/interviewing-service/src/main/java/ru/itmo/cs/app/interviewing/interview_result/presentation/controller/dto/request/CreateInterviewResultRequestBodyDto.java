package ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.Verdict;

public record CreateInterviewResultRequestBodyDto(
        @JsonProperty("feedback_id") String feedbackId,
        @JsonProperty("verdict") Verdict verdict
) {}
