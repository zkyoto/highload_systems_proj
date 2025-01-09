package ru.ifmo.cs.feedbacks.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.ifmo.cs.feedbacks.domain.value.Comment;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
import ru.ifmo.cs.feedbacks.domain.value.Grade;

class FeedbackTest {
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        feedback = Feedback.create("interviewId");
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
