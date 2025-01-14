package ru.ifmo.cs.candidates.presentation.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

@OpenAPIDefinition(
        security = @SecurityRequirement(name = "Bearer Authentication"),
        servers = {
                @Server(url = "/", description = "Gateway server")
        }
)
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Candidates API", description = "API для управления кандидатами")
public class CandidatesApiController {
    private final CommandBus commandBus;
    private final CandidateRepository candidateRepository;
    private final CandidatePageQueryService candidatePageQueryService;

    @PostMapping("/api/v1/candidates/add")
    @Operation(summary = "Добавить кандидата", description = "Добавляет нового кандидата в систему")
    public ResponseEntity<?> addCandidate(
            @RequestBody @Parameter(description = "DTO для создания кандидата") AddCandidateRequestBodyDto addCandidateRequestBodyDto
    ) {
        Name candidateName = Name.of(addCandidateRequestBodyDto.candidateFullName());
        commandBus.submit(new AddCandidateCommand(candidateName));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/candidates/by-id")
    @Operation(summary = "Получить кандидата по ID", description = "Возвращает информацию о кандидате на основе его идентификатора")
    public ResponseEntity<?> getCandidateById(
            @RequestParam(name = "candidate_id") @Parameter(description = "Идентификатор кандидата") String candidateId
    ) {
        Candidate candidate = candidateRepository.findById(CandidateId.hydrate(candidateId));
        return ResponseEntity.ok(new GetCandidateResponseBodyDto(CandidateResponseDto.from(candidate)));
    }

    @GetMapping("/api/v1/candidates")
    @Operation(summary = "Получить всех кандидатов", description = "Возвращает список всех кандидатов с поддержкой пагинации")
    public ResponseEntity<?> getAllCandidates(
            @RequestParam(name = "page", defaultValue = "0") @Parameter(description = "Номер страницы") int page,
            @RequestParam(name = "size", defaultValue = "50") @Parameter(description = "Размер страницы") int size,
            HttpServletResponse response
    ) {
        CandidatePage candidatePage = candidatePageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count",


                String.valueOf(candidatePage.totalElements()));
        return ResponseEntity.ok(new GetAllCandidatesResponseBodyDto(candidatePage.content()
                .stream()
                .map(CandidateResponseDto::from)
                .toList()));
    }
}