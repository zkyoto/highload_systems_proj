package ru.itmo.cs.app.interviewing.interviewer.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ifmo.cs.misc.UserId;

public record AddInterviewerRequestBodyDto(@JsonProperty("user_id") UserId userId) {}
