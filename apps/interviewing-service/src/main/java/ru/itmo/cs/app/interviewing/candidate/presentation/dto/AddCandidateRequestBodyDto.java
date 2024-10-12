package ru.itmo.cs.app.interviewing.candidate.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record AddCandidateRequestBodyDto(@JsonProperty("candidate_name") @Nullable String candidateFullName) {}
