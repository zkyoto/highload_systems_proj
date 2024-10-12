package ru.itmo.cs.app.interviewing.candidate.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.application.command.AddCandidateCommand;
import ru.itmo.cs.app.interviewing.candidate.presentation.controller.dto.request.AddCandidateRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController("/api/v1/candidates")
@AllArgsConstructor
public class CandidatesApiController {
    private final CommandBus commandBus;

    @PostMapping("/v1/add")
    public ResponseEntity<?> addCandidate(
            @RequestBody AddCandidateRequestBodyDto addCandidateRequestBodyDto
    ) {
        Name candidateName = Name.of(addCandidateRequestBodyDto.candidateFullName());
        commandBus.submit(new AddCandidateCommand(candidateName));
        return ResponseEntity.ok().build();
    }
}
