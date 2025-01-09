package ru.ifmo.cs.interviews.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.cs.interviews.application.command.CancelInterviewCommand;
import ru.ifmo.cs.interviews.application.command.RescheduleInterviewCommand;
import ru.ifmo.cs.interviews.application.command.ScheduleInterviewCommand;
import ru.ifmo.cs.interviews.application.query.InterviewPageQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewPage;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;
import ru.ifmo.cs.interviews.domain.value.InterviewId;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.CancelInterviewRequestBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.RescheduleInterviewRequestBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.request.ScheduleInterviewRequestBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.response.GetAllInterviewsResponseBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.response.GetInterviewResponseBodyDto;
import ru.ifmo.cs.interviews.presentation.controller.dto.response.InterviewResponseDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class InterviewsApiController {
    private final CommandBus commandBus;
    private final InterviewRepository interviewRepository;
    private final InterviewPageQueryService interviewPageQueryService;

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

    @PostMapping("/api/v1/interviews/reschedule")
    public ResponseEntity<?> rescheduleInterview(
            @RequestBody RescheduleInterviewRequestBodyDto rescheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new RescheduleInterviewCommand(
                InterviewId.hydrate(rescheduleInterviewRequestBodyDto.interviewId()),
                rescheduleInterviewRequestBodyDto.newScheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interviews/cancel")
    public ResponseEntity<?> cancelInterview(
            @RequestBody CancelInterviewRequestBodyDto cancelInterviewRequestBodyDto
    ) {
        commandBus.submit(new CancelInterviewCommand(InterviewId.hydrate(cancelInterviewRequestBodyDto.interviewId())));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/interviews/by-id")
    public ResponseEntity<?> getInterviewById(@RequestParam String interviewId) {
        Interview interview = interviewRepository.findById(InterviewId.hydrate(interviewId));
        return ResponseEntity.ok(new GetInterviewResponseBodyDto(InterviewResponseDto.from(interview)));
    }

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
