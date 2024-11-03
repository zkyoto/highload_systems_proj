package ru.itmo.cs.app.interviewing.candidate.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;

public record CandidatePage(
        List<Candidate> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Candidate> {}
