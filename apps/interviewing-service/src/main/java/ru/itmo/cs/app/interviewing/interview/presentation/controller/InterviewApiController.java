package ru.itmo.cs.app.interviewing.interview.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.candidate.domain.value.CandidateId;
import ru.itmo.cs.app.interviewing.interview.application.command.CancelInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.RescheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.ScheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.CancelInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.RescheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.controller.dto.request.ScheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class InterviewApiController {
    private final CommandBus commandBus;

    @PostMapping("/api/v1/interview/schedule")
    public ResponseEntity<?> scheduleInterview(
            @RequestBody ScheduleInterviewRequestBodyDto scheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new ScheduleInterviewCommand(
                InterviewerId.hydrate(scheduleInterviewRequestBodyDto.interviewerId()),
                CandidateId.hydrate(scheduleInterviewRequestBodyDto.candidateId()),
                scheduleInterviewRequestBodyDto.scheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interview/reschedule")
    public ResponseEntity<?> rescheduleInterview(
            @RequestBody RescheduleInterviewRequestBodyDto rescheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new RescheduleInterviewCommand(
                InterviewId.hydrate(rescheduleInterviewRequestBodyDto.interviewId()),
                rescheduleInterviewRequestBodyDto.newScheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interview/cancel")
    public ResponseEntity<?> cancelInterview(
            @RequestBody CancelInterviewRequestBodyDto cancelInterviewRequestBodyDto
    ) {
        commandBus.submit(new CancelInterviewCommand(InterviewId.hydrate(cancelInterviewRequestBodyDto.interviewId())));
        return ResponseEntity.ok().build();
    }
}
