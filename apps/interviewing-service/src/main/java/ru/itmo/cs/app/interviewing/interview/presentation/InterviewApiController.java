package ru.itmo.cs.app.interviewing.interview.presentation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.interview.application.command.CancelInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.RescheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.application.command.ScheduleInterviewCommand;
import ru.itmo.cs.app.interviewing.interview.presentation.dto.CancelInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.dto.RescheduleInterviewRequestBodyDto;
import ru.itmo.cs.app.interviewing.interview.presentation.dto.ScheduleInterviewRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class InterviewApiController {
    private final CommandBus commandBus;

    @PostMapping("/api/v1/interview/schedule")
    public ResponseEntity<?> scheduleInterview(
            @RequestBody ScheduleInterviewRequestBodyDto scheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new ScheduleInterviewCommand(scheduleInterviewRequestBodyDto.interviewerId(),
                                                       scheduleInterviewRequestBodyDto.candidateId(),
                                                       scheduleInterviewRequestBodyDto.scheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interview/reschedule")
    public ResponseEntity<?> rescheduleInterview(
            @RequestBody RescheduleInterviewRequestBodyDto rescheduleInterviewRequestBodyDto
    ) {
        commandBus.submit(new RescheduleInterviewCommand(rescheduleInterviewRequestBodyDto.interviewId(),
                                                         rescheduleInterviewRequestBodyDto.newScheduledTime()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interview/cancel")
    public ResponseEntity<?> cancelInterview(
            @RequestBody CancelInterviewRequestBodyDto cancelInterviewRequestBodyDto
    ) {
        commandBus.submit(new CancelInterviewCommand(cancelInterviewRequestBodyDto.interviewId()));
        return ResponseEntity.ok().build();
    }
}
