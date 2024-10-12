package ru.itmo.cs.app.interviewing.feedback.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackRewrittenEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackSubmittedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Feedback {
    private final FeedbackId id;
    private final Instant createdAt;
    @NonNull private Instant updatedAt;
    private final InterviewId interviewId;
    @NonNull private FeedbackStatus status;
    private Grade grade;
    private Comment comment;
    private List<FeedbackEvent> events = new LinkedList<>();

    public static Feedback create(InterviewId interviewId) {
        Instant now = Instant.now();
        Feedback feedback = new Feedback(FeedbackId.generate(),
                                         now,
                                         now,
                                         interviewId,
                                         FeedbackStatus.WAITING_FOR_SUBMIT);
        feedback.events.add(FeedbackCreatedEvent.fromEntity(feedback));
        return feedback;
    }

    public void setGrade(int value) {
        ensureGradeShouldNotBeAlreadySet();
        this.grade = Grade.of(value);
        this.updatedAt = Instant.now();
    }

    public void setComment(String value) {
        ensureCommentShouldNotBeAlreadySet();
        this.comment = Comment.of(value);
        this.updatedAt = Instant.now();
    }

    public void submit() {
        ensureCommentAndGradeShouldBeSet();
        this.status = FeedbackStatus.SUBMITTED;
        this.updatedAt = Instant.now();
        this.events.add(FeedbackSubmittedEvent.fromEntity(this));
    }

    public void rewrite(int grade, String comment) {
        this.grade = Grade.of(grade);
        this.comment = Comment.of(comment);
        this.updatedAt = Instant.now();
        this.events.add(FeedbackRewrittenEvent.fromEntity(this));
    }

    public List<FeedbackEvent> releaseEvents() {
        List<FeedbackEvent> releasedEvents = events;
        events = new LinkedList<>();
        return releasedEvents;
    }

    private void ensureCommentAndGradeShouldBeSet() {
        if (grade == null) {
            throw new IllegalStateException("Grade is not set");
        }
        if (comment == null) {
            throw new IllegalStateException("Comment is not set");
        }
    }

    private void ensureGradeShouldNotBeAlreadySet() {
        if (this.grade != null) {
            throw new IllegalStateException("Grade is already set");
        }
    }

    private void ensureCommentShouldNotBeAlreadySet() {
        if (this.comment != null) {
            throw new IllegalStateException("Comment is already set");
        }
    }

}
