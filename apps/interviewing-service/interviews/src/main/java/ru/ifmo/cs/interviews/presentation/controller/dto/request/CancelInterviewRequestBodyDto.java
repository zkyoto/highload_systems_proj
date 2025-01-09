package ru.ifmo.cs.interviews.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelInterviewRequestBodyDto(@JsonProperty("interview_id") String interviewId) {}
