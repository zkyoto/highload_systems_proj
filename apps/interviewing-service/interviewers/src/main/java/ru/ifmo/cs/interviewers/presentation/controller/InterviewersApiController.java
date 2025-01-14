package ru.ifmo.cs.interviewers.presentation.controller;

import java.util.NoSuchElementException;

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
import ru.ifmo.cs.interviewers.application.command.ActivateInterviewerCommand;
import ru.ifmo.cs.interviewers.application.command.AddInterviewerCommand;
import ru.ifmo.cs.interviewers.application.command.DemoteInterviewerCommand;
import ru.ifmo.cs.interviewers.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.ifmo.cs.interviewers.application.query.InterviewersPageQueryService;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewersPage;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.ActivateInterviewerRequestBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.AddInterviewerRequestBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.request.DemoteInterviewerRequestBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.response.GetAllInterviewersResponseBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.response.GetInterviewerResponseBodyDto;
import ru.ifmo.cs.interviewers.presentation.controller.dto.response.InterviewerResponseDto;
import ru.itmo.cs.command_bus.CommandBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
public class InterviewersApiController {
    private final CommandBus commandBus;
    private final InterviewerUniqueIdentifiersQueryService interviewerUniqueIdentifiersQueryService;
    private final InterviewerRepository interviewerRepository;
    private final InterviewersPageQueryService interviewersPageQueryService;

    @Operation(summary = "Add a new interviewer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interviewer added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/api/v1/interviewers/add")
    public ResponseEntity<?> addInterviewer(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the interviewer to add",
                    content = @Content(schema = @Schema(implementation = AddInterviewerRequestBodyDto.class))) AddInterviewerRequestBodyDto addInterviewerRequestBodyDto
    ) {
        commandBus.submit(new AddInterviewerCommand(addInterviewerRequestBodyDto.userId()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate an interviewer")
    @PostMapping("/api/v1/interviewers/activate")
    public ResponseEntity<?> activateInterviewer(
            @RequestBody


            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the interviewer to activate",
                    content = @Content(schema = @Schema(implementation = ActivateInterviewerRequestBodyDto.class))) ActivateInterviewerRequestBodyDto activateInterviewerRequestBodyDto
    ) {
        InterviewerUniqueIdentifiersDto interviewerUniqueIds;
        interviewerUniqueIds =
                interviewerUniqueIdentifiersQueryService.findBy(activateInterviewerRequestBodyDto.userId());
        commandBus.submit(new ActivateInterviewerCommand(interviewerUniqueIds.interviewerId()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Demote an interviewer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interviewer demoted successfully"),
            @ApiResponse(responseCode = "404", description = "Interviewer not found", content = @Content)
    })
    @PostMapping("/api/v1/interviewers/demote")
    public ResponseEntity<?> demoteInterviewer(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the interviewer to demote",
                    content = @Content(schema = @Schema(implementation = DemoteInterviewerRequestBodyDto.class))) DemoteInterviewerRequestBodyDto demoteInterviewerRequestBodyDto
    ) {
        InterviewerUniqueIdentifiersDto interviewerUniqueIds;
        try {
            interviewerUniqueIds =
                    interviewerUniqueIdentifiersQueryService.findBy(demoteInterviewerRequestBodyDto.userId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        commandBus.submit(new DemoteInterviewerCommand(interviewerUniqueIds.interviewerId()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get details of an interviewer by ID")
    @GetMapping("/api/v1/interviewers/by-id")
    public ResponseEntity<?> getInterviewerById(
            @RequestParam(name = "interviewer_id")
            @Parameter(description = "ID of the interviewer", required = true) String interviewerId
    ) {
        Interviewer interviewer = interviewerRepository.findById(InterviewerId.hydrate(interviewerId));
        return ResponseEntity.ok().body(new GetInterviewerResponseBodyDto(InterviewerResponseDto.from(interviewer)));
    }

    @Operation(summary = "Get a list of all interviewers")
    @GetMapping("/api/v1/interviewers")
    public ResponseEntity<?> getAllInterviewers(
            @RequestParam(defaultValue = "0")
            @Parameter(description = "Page number", example = "0") int page,
            @RequestParam(defaultValue = "50")
            @Parameter(description = "Page size", example = "50") int size,
            HttpServletResponse response
    ) {
        InterviewersPage interviewersPage = interviewersPageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(interviewersPage.totalElements()));
        return ResponseEntity.ok(
                new GetAllInterviewersResponseBodyDto(interviewersPage.content()
                        .stream()
                        .map(InterviewerResponseDto::from)
                        .toList()));
    }
}
