package ru.itmo.cs.app.interviewing.interviewer.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.libs.page.Page;

public record InterviewersPage(
        List<Interviewer> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interviewer> {}
