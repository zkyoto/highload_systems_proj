package ru.itmo.cs.app.interviewing.feedback.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.internal.matchers.And;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;

class FeedbackTest {
    private Feedback feedback;
    private InterviewId interviewId;

    @BeforeEach
    void setUp() {
        interviewId = InterviewId.generate();
        feedback = Feedback.create(interviewId);
    }

    @Test
    void ensureFeedbackCanBeCreatedSuccessfully() {
        assertNotNull(feedback.getId());
        assertEquals(FeedbackStatus.WAITING_FOR_SUBMIT, feedback.getStatus());
        assertNotNull(feedback.getCreatedAt());
        assertFalse(feedback.getEvents().isEmpty());
    }

    @Test
    void verifyThatGradeCanBeSetForFeedback() {
        Grade grade = Grade.of(5);
        feedback.setGrade(grade);
        assertEquals(grade, feedback.getGrade());
        assertNotNull(feedback.getUpdatedAt());
    }

    @Test
    void ensureThatGradeCannotBeSetMultipleTimes() {
        Grade grade = Grade.of(5);
        feedback.setGrade(grade);
        assertThrows(IllegalStateException.class, () -> feedback.setGrade(Grade.of(4)));
    }

    @Test
    void validateThatCommentCanBeAddedToFeedback() {
        Comment comment = Comment.of("Good job");
        feedback.setComment(comment);
        assertEquals(comment, feedback.getComment());
        assertNotNull(feedback.getUpdatedAt());
    }

    @Test
    void ensureFeedbackSubmissionRequiresBothGradeAndComment() {
        feedback.setGrade(Grade.of(5));
        assertThrows(IllegalStateException.class, feedback::submit);
    }

}
