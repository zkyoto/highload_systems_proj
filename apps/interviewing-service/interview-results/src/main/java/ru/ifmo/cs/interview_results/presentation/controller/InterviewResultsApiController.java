package ru.ifmo.cs.interview_results.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

@RestController()
@AllArgsConstructor
public class InterviewResultsApiController {
    private final CommandBus commandBus;
    private final InterviewResultRepository interviewResultRepository;
    private final InterviewResultPageQueryService interviewResultPageQueryService;

    @PostMapping("/api/v1/interview-results/create")
    public ResponseEntity<?> createInterviewResult(
            @RequestBody CreateInterviewResultRequestBodyDto createInterviewResultRequestBodyDto
    ) {
        commandBus.submit(
                new CreateInterviewResultCommand(createInterviewResultRequestBodyDto.feedbackId(),
                        createInterviewResultRequestBodyDto.verdict())
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/interview-results/by-id")
    public ResponseEntity<?> getInterviewerById(
            @RequestParam(name = "interview_result_id") String interviewResultId
    ) {
        InterviewResult interviewResult =
                interviewResultRepository.findById(InterviewResultId.hydrate(interviewResultId));
        return ResponseEntity.ok()
                .body(new GetInterviewResultResponseBodyDto(InterviewResultResponseDto.from(interviewResult)));
    }

    @GetMapping("/api/v1/interview-results")
    public ResponseEntity<?> getAllInterviewers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size,
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
