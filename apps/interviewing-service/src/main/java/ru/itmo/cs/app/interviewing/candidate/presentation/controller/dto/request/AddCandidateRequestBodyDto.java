package ru.itmo.cs.app.interviewing.candidate.presentation.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record AddCandidateRequestBodyDto(@JsonProperty("candidate_name") @Nullable String candidateFullName) {}
