package ru.ifmo.cs.interview_results.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.ifmo.cs.interview_results.domain.InterviewResult;

public record InterviewResultPage(
        List<InterviewResult> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<InterviewResult> {}
