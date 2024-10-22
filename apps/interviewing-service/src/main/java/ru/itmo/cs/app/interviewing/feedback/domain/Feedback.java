package ru.itmo.cs.app.interviewing.feedback.domain;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    public static Feedback hydrate(
            String id,
            Instant createdAt,
            Instant updatedAt,
            String interviewId,
            String status,
            @Nullable Integer grade,
            @Nullable String comment
    ) {
        Feedback feedback = new Feedback(
                FeedbackId.hydrate(id),
                createdAt,
                updatedAt,
                InterviewId.hydrate(interviewId),
                FeedbackStatus.R.fromValue(status)
        );
        if (grade != null) {
            feedback.grade = Grade.of(grade);
        }
        if (comment != null) {
            feedback.comment = Comment.of(comment);
        }
        return feedback;
    }

    public void setGrade(Grade grade) {
        ensureGradeShouldNotBeAlreadySet();
        this.grade = grade;
        this.updatedAt = Instant.now();
    }

    public void setComment(Comment comment) {
        ensureCommentShouldNotBeAlreadySet();
        this.comment = comment;
        this.updatedAt = Instant.now();
    }

    public void submit() {
        ensureCommentAndGradeShouldBeSet();
        this.status = FeedbackStatus.SUBMITTED;
        this.updatedAt = Instant.now();
        this.events.add(FeedbackSubmittedEvent.fromEntity(this));
    }

    public void rewrite(Grade grade, Comment comment) {
        this.grade = grade;
        this.comment = comment;
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
