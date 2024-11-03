package ru.itmo.cs.app.interviewing.interview.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;

public record InterviewPage(
        List<Interview> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interview> {}
