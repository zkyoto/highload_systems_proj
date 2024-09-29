package ru.itmo.cs.app.interviewing.interviewer.presentation;

import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.interviewer.application.command.ActivateInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.command.AddInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.command.DemoteInterviewerCommand;
import ru.itmo.cs.app.interviewing.interviewer.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.itmo.cs.app.interviewing.interviewer.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.itmo.cs.app.interviewing.interviewer.presentation.dto.AddInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.dto.ActivateInterviewerRequestBodyDto;
import ru.itmo.cs.app.interviewing.interviewer.presentation.dto.DemoteInterviewerRequestBodyDto;
import ru.itmo.cs.command_bus.CommandBus;

@AllArgsConstructor
@RestController
public class InterviewersApiController {
    private final CommandBus commandBus;
    private final InterviewerUniqueIdentifiersQueryService interviewerUniqueIdentifiersQueryService;

    @PostMapping("/api/v1/interviewers/add")
    public ResponseEntity addInterviewer(
            @RequestBody AddInterviewerRequestBodyDto addInterviewerRequestBodyDto
    ) {
        commandBus.submit(new AddInterviewerCommand(addInterviewerRequestBodyDto.userId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interviewers/activate")
    public ResponseEntity activateInterviewer(
            @RequestBody ActivateInterviewerRequestBodyDto activateInterviewerRequestBodyDto
    ) {
        InterviewerUniqueIdentifiersDto interviewerUniqueIds;
        try {
            interviewerUniqueIds =
                    interviewerUniqueIdentifiersQueryService.findBy(activateInterviewerRequestBodyDto.userId());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        commandBus.submit(new ActivateInterviewerCommand(interviewerUniqueIds.interviewerId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/interviewers/demote")
    public ResponseEntity demoteInterviewer(
            @RequestBody DemoteInterviewerRequestBodyDto demoteInterviewerRequestBodyDto
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
}
