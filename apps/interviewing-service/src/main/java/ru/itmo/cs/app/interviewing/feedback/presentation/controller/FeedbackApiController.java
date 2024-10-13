package ru.itmo.cs.app.interviewing.feedback.presentation.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.cs.app.interviewing.feedback.application.command.CreateFeedbackForInterviewCommand;
import ru.itmo.cs.app.interviewing.feedback.application.command.RewriteFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.command.SaveCommentFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.command.SaveGradeFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.command.SubmitFeedbackCommand;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbacksPendingResultQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.CreateFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.RewriteFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SaveCommentFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SaveGradeFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.request.SubmitFeedbackRequestBodyDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response.FeedbackPendingResultDto;
import ru.itmo.cs.app.interviewing.feedback.presentation.controller.dto.response.FeedbacksPendingResultResponseBodyDto;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.command_bus.CommandBus;

@RestController
@AllArgsConstructor
public class FeedbackApiController {
    private final CommandBus commandBus;
    private final FeedbacksPendingResultQueryService feedbacksPendingResultQueryService;

    @PostMapping("/api/v1/feedback/create")
    public ResponseEntity<?> createFeedback(
            @RequestBody CreateFeedbackRequestBodyDto createFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new CreateFeedbackForInterviewCommand(
                        InterviewId.hydrate(createFeedbackRequestBodyDto.interviewId())
                )
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedback/grade/save")
    public ResponseEntity<?> saveGrade(
            @RequestBody SaveGradeFeedbackRequestBodyDto saveGradeFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SaveGradeFeedbackCommand(FeedbackId.hydrate(saveGradeFeedbackRequestBodyDto.feedbackId()),
                Grade.of(saveGradeFeedbackRequestBodyDto.grade())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedback/comment/save")
    public ResponseEntity<?> saveComment(
            @RequestBody SaveCommentFeedbackRequestBodyDto saveCommentFeedbackRequestBodyDto
    ) {
        commandBus.submit(
                new SaveCommentFeedbackCommand(FeedbackId.hydrate(saveCommentFeedbackRequestBodyDto.feedbackId()),
                        Comment.of(saveCommentFeedbackRequestBodyDto.comment()))
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedback/rewrite")
    public ResponseEntity<?> rewriteFeedback(
            @RequestBody RewriteFeedbackRequestBodyDto rewriteFeedbackRequestBodyDto
    ) {
        commandBus.submit(new RewriteFeedbackCommand(FeedbackId.hydrate(rewriteFeedbackRequestBodyDto.feedbackId()),
                Grade.of(rewriteFeedbackRequestBodyDto.grade()),
                Comment.of(rewriteFeedbackRequestBodyDto.comment())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/feedback/submit")
    public ResponseEntity<?> submitFeedback(
            @RequestBody SubmitFeedbackRequestBodyDto submitFeedbackRequestBodyDto
    ) {
        commandBus.submit(new SubmitFeedbackCommand(FeedbackId.hydrate(submitFeedbackRequestBodyDto.feedbackId())));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/feedback/pending-result")
    public ResponseEntity<?> getFeedbacksPendingResult() {
        List<FeedbackPendingResultDto> feedbackPendingResultDtoList =
                feedbacksPendingResultQueryService.findAll()
                                                  .stream()
                                                  .map(FeedbackPendingResultDto::from)
                                                  .toList();

        return ResponseEntity.ok(new FeedbacksPendingResultResponseBodyDto(feedbackPendingResultDtoList));
    }

}