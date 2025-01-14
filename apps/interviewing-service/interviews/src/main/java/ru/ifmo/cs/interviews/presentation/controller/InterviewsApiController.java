package ru.ifmo.cs.interviews.presentation.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.ifmo.cs.interviews.application.command.*;
import ru.ifmo.cs.interviews.application.query.InterviewPageQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewPage;
import ru.ifmo.cs.interviews.domain.*;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.*;
import ru.ifmo.cs.interviews.presentation.controller.dto.response.*;
import ru.itmo.cs.command_bus.CommandBus;

import jakarta.servlet.http.HttpServletResponse;

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
@CrossOrigin(originPatterns = "*")
@RestController
@AllArgsConstructor
public class InterviewsApiController {

    private final CommandBus commandBus;
    private final InterviewRepository interviewRepository;
    private final InterviewPageQueryService interviewPageQueryService;

    @Operation(summary = "Schedule a new interview", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview scheduled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/api/v1/interviews/schedule")
    public ResponseEntity<?> scheduleInterview(
            @RequestBody ScheduleInterviewRequestBodyDto scheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new ScheduleInterviewCommand(
                scheduleInterviewRequestBodyDto.interviewerId(),
                scheduleInterviewRequestBodyDto.candidateId(),
                scheduleInterviewRequestBodyDto.scheduledTime()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reschedule an existing interview", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview rescheduled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/api/v1/interviews/reschedule")
    public ResponseEntity<?> rescheduleInterview(
            @RequestBody RescheduleInterviewRequestBodyDto rescheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new RescheduleInterviewCommand(
                InterviewId.hydrate(rescheduleInterviewRequestBodyDto.interviewId()),


                rescheduleInterviewRequestBodyDto.newScheduledTime()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancel an interview", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview canceled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/api/v1/interviews/cancel")
    public ResponseEntity<?> cancelInterview(
            @RequestBody CancelInterviewRequestBodyDto cancelInterviewRequestBodyDto
    ) {
        commandBus.submit(new CancelInterviewCommand(InterviewId.hydrate(cancelInterviewRequestBodyDto.interviewId())));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get interview details by ID", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview details", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetInterviewResponseBodyDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Interview not found", content = @Content)
    })
    @GetMapping("/api/v1/interviews/by-id")
    public ResponseEntity<?> getInterviewById(
            @RequestParam String interviewId) {
        Interview interview = interviewRepository.findById(InterviewId.hydrate(interviewId));
        return ResponseEntity.ok(new GetInterviewResponseBodyDto(InterviewResponseDto.from(interview)));
    }

    @Operation(summary = "Get a list of all interviews", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of interviews", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetAllInterviewsResponseBodyDto.class))
            })
    })
    @GetMapping("/api/v1/interviews")
    public ResponseEntity<?> getAllInterviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletResponse response
    ) {
        InterviewPage interviewPage = interviewPageQueryService.findFor(page, size);
        response.setHeader("X-Total-Count", String.valueOf(interviewPage.totalElements()));
        return ResponseEntity.ok(
                new GetAllInterviewsResponseBodyDto(interviewPage.content()
                        .stream()
                        .map(InterviewResponseDto::from)
                        .toList()));
    }
}