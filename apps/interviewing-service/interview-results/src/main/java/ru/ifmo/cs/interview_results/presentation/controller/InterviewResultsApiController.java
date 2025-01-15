package ru.ifmo.cs.interview_results.presentation.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.cs.interview_results.application.command.CreateInterviewResultCommand;
import ru.ifmo.cs.interview_results.application.query.InterviewResultPageQueryService;
import ru.ifmo.cs.interview_results.application.query.dto.InterviewResultPage;
import ru.ifmo.cs.interview_results.domain.InterviewResult;
import ru.ifmo.cs.interview_results.domain.InterviewResultRepository;
import ru.ifmo.cs.interview_results.domain.value.InterviewResultId;
import ru.ifmo.cs.interview_results.presentation.controller.dto.request.CreateInterviewResultRequestBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.GetAllInterviewResultsResponseBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.GetInterviewResultResponseBodyDto;
import ru.ifmo.cs.interview_results.presentation.controller.dto.response.InterviewResultResponseDto;
import ru.itmo.cs.command_bus.CommandBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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
@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/v1/interview-results")
public class InterviewResultsApiController {
    private final CommandBus commandBus;
    private final InterviewResultRepository interviewResultRepository;
    private final InterviewResultPageQueryService interviewResultPageQueryService;

    @Operation(summary = "Создать результат собеседования",
            description = "Создает новый результат собеседования на основе предоставленных данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное создание"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в запросе",
                            content = @Content)
            })
    @PostMapping("/create")
    public ResponseEntity<?> createInterviewResult(
            @RequestBody CreateInterviewResultRequestBodyDto createInterviewResultRequestBodyDto
    ) {
        commandBus.submit(
                new CreateInterviewResultCommand(createInterviewResultRequestBodyDto.feedbackId(),
                        createInterviewResultRequestBodyDto.verdict())
        );
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить результат собеседования по ID",
            description = "Возвращает результаты собеседования для заданного идентификатора.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно найден результат",
                            content = @Content(schema = @Schema(implementation =
                                    GetInterviewResultResponseBodyDto.class))),
                    @ApiResponse(responseCode = "404", description = "Результат не найден",
                            content = @Content)
            })
    @GetMapping("/by-id")
    public ResponseEntity<?> getInterviewerById(
            @Parameter(description = "ID результата собеседования", required = true)
            @RequestParam(name = "interview_result_id") String interviewResultId
    ) {
        InterviewResult interviewResult =


                interviewResultRepository.findById(InterviewResultId.hydrate(interviewResultId));
        return ResponseEntity.ok()
                .body(new GetInterviewResultResponseBodyDto(InterviewResultResponseDto.from(interviewResult)));
    }

    @Operation(summary = "Получить все результаты собеседования",
            description = "Возвращает страницу результатов собеседования.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получили результаты",
                            content = @Content(schema = @Schema(implementation =
                                    GetAllInterviewResultsResponseBodyDto.class)))
            })
    @GetMapping
    public ResponseEntity<?> getAllInterviewers(
            @Parameter(description = "Номер страницы") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(name = "size", defaultValue = "50") int size,
            HttpServletResponse response
    ) {
        InterviewResultPage interviewResultPage = interviewResultPageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(interviewResultPage.totalElements()));
        return ResponseEntity.ok(
                new GetAllInterviewResultsResponseBodyDto(interviewResultPage.content()
                        .stream()
                        .map(InterviewResultResponseDto::from)
                        .toList()));
    }
}