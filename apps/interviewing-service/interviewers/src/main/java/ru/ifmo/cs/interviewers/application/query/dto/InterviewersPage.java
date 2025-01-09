package ru.ifmo.cs.interviewers.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.ifmo.cs.interviewers.domain.Interviewer;

public record InterviewersPage(
        List<Interviewer> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Interviewer> {}
