package ru.itmo.cs.app.interviewing.interview_result.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.interview_result.application.command.CreateInterviewResultCommand;
import ru.itmo.cs.app.interviewing.interview_result.presentation.controller.dto.request.CreateInterviewResultRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController()
@AllArgsConstructor
public class InterviewResultApiController {
    private final CommandBus commandBus;

    @PostMapping("/api/v1/interview-result/create")
    public ResponseEntity<?> createInterviewResult(
            @RequestBody CreateInterviewResultRequestBodyDto createInterviewResultRequestBodyDto
    ) {
        commandBus.submit(
                new CreateInterviewResultCommand(FeedbackId.hydrate(createInterviewResultRequestBodyDto.feedbackId()),
                                                 createInterviewResultRequestBodyDto.verdict())
        );
        return ResponseEntity.ok().build();
    }

}
