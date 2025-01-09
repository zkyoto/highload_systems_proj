package ru.ifmo.cs.interviewers.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.misc.UserId;

public record AddInterviewerRequestBodyDto(@JsonProperty("user_id") UserId userId) {}
