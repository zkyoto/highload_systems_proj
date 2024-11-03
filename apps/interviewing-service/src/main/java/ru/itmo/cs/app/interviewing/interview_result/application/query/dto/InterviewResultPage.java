package ru.itmo.cs.app.interviewing.interview_result.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;

public record InterviewResultPage(
        List<InterviewResult> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<InterviewResult> {}
