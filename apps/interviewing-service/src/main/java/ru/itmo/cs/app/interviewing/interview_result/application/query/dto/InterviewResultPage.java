package ru.itmo.cs.app.interviewing.interview_result.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.libs.page.domain.Page;

public record InterviewResultPage(
        List<InterviewResult> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<InterviewResult> {}
