package ru.itmo.cs.app.interviewing.candidate.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;
import ru.itmo.cs.app.interviewing.libs.page.domain.Page;

public record CandidatePage(
        List<Candidate> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Candidate> {}
