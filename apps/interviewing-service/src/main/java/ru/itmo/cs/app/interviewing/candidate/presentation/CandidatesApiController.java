package ru.itmo.cs.app.interviewing.candidate.presentation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.misc.Name;
import ru.itmo.cs.app.interviewing.candidate.application.command.AddCandidateCommand;
import ru.itmo.cs.app.interviewing.candidate.presentation.dto.AddCandidateRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class CandidatesApiController {
    private final CommandBus commandBus;

    @PostMapping("/api/v1/candidates/add")
    public ResponseEntity<?> addCandidate(
            @RequestBody AddCandidateRequestBodyDto addCandidateRequestBodyDto
    ) {
        Name candidateName = Name.of(addCandidateRequestBodyDto.candidateFullName());
        commandBus.submit(new AddCandidateCommand(candidateName));
        return ResponseEntity.ok().build();
    }
}
