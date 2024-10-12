package ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public record CancelInterviewRequestBodyDto(@JsonProperty("interview_id") InterviewId interviewId) {}
