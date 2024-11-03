package ru.itmo.cs.app.interviewing.interviewer.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;

public record InterviewersPage(
        List<Interviewer> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interviewer> {}
