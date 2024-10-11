package ru.itmo.cs.app.interviewing.candidate.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import ru.ifmo.cs.misc.Name;

public record AddCandidateRequestBodyDto(@JsonProperty("candidate_name") @Nullable String candidateFullName) {}
