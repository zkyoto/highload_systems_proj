package ru.ifmo.cs.candidates.application.query.dto;

import java.util.List;

import ru.ifmo.cs.page.domain.Page;
import ru.ifmo.cs.candidates.domain.Candidate;

public record CandidatePage(
        List<Candidate> content,
        int pageNumber,
        int pageSize,
        long totalElements
) implements Page<Candidate> {}
