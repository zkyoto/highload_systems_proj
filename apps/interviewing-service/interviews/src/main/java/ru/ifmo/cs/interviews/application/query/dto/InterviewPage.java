package ru.ifmo.cs.interviews.application.query.dto;

import java.util.List;

import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.page.domain.Page;

public record InterviewPage(
        List<Interview> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interview> {}
