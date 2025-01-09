package ru.ifmo.cs.candidates.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.candidates.application.command.AddCandidateCommand;
import ru.ifmo.cs.candidates.application.query.CandidatePageQueryService;
import ru.ifmo.cs.candidates.application.query.dto.CandidatePage;
import ru.ifmo.cs.candidates.domain.Candidate;
import ru.ifmo.cs.candidates.domain.CandidateRepository;
import ru.ifmo.cs.candidates.domain.value.CandidateId;
import ru.ifmo.cs.candidates.presentation.controller.dto.request.AddCandidateRequestBodyDto;
import ru.ifmo.cs.candidates.presentation.controller.dto.response.CandidateResponseDto;
import ru.ifmo.cs.candidates.presentation.controller.dto.response.GetAllCandidatesResponseBodyDto;
import ru.ifmo.cs.candidates.presentation.controller.dto.response.GetCandidateResponseBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class CandidatesApiController {
    private final CommandBus commandBus;
    private final CandidateRepository candidateRepository;
    private final CandidatePageQueryService candidatePageQueryService;

    @PostMapping("/api/v1/candidates/add")
    public ResponseEntity<?> addCandidate(
            @RequestBody AddCandidateRequestBodyDto addCandidateRequestBodyDto
    ) {
        Name candidateName = Name.of(addCandidateRequestBodyDto.candidateFullName());
        commandBus.submit(new AddCandidateCommand(candidateName));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/candidates/by-id")
    public ResponseEntity<?> getCandidateById(@RequestParam(name = "candidate_id") String candidateId) {
        Candidate candidate = candidateRepository.findById(CandidateId.hydrate(candidateId));
        return ResponseEntity.ok(new GetCandidateResponseBodyDto(CandidateResponseDto.from(candidate)));
    }

    @GetMapping("/api/v1/candidates")
    public ResponseEntity<?> getAllCandidates(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
            HttpServletResponse response
    ) {
        CandidatePage candidatePage = candidatePageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(candidatePage.totalElements()));
        return ResponseEntity.ok(new GetAllCandidatesResponseBodyDto(candidatePage.content()
                .stream()
                .map(CandidateResponseDto::from)
                .toList()));
    }
}
