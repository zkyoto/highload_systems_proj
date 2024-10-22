package ru.itmo.cs.app.interviewing.interview.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.libs.page.domain.Page;

public record InterviewPage(
        List<Interview> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interview> {}
