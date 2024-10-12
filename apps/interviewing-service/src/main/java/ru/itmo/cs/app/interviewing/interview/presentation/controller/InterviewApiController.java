package ru.itmo.cs.app.interviewing.interview.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.interview.application.command.CancelInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.RescheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.ScheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.CancelInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.RescheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.ScheduleInterviewRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController("/api/interview")
@AllArgsConstructor
public class InterviewApiController {
    private final CommandBus commandBus;

    @PostMapping("/v1/schedule")
    public ResponseEntity<?> scheduleInterview(
            @RequestBody ScheduleInterviewRequestBodyDto scheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new ScheduleInterviewCommand(scheduleInterviewRequestBodyDto.interviewerId(),
                                                       scheduleInterviewRequestBodyDto.candidateId(),
                                                       scheduleInterviewRequestBodyDto.scheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/reschedule")
    public ResponseEntity<?> rescheduleInterview(
            @RequestBody RescheduleInterviewRequestBodyDto rescheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new RescheduleInterviewCommand(rescheduleInterviewRequestBodyDto.interviewId(),
                                                         rescheduleInterviewRequestBodyDto.newScheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/cancel")
    public ResponseEntity<?> cancelInterview(
            @RequestBody CancelInterviewRequestBodyDto cancelInterviewRequestBodyDto
    ) {
        commandBus.submit(new CancelInterviewCommand(cancelInterviewRequestBodyDto.interviewId()));
        return ResponseEntity.ok().build();
    }
}
