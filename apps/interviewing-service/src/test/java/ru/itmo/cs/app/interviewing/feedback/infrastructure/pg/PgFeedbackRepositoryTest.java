package ru.itmo.cs.app.interviewing.feedback.infrastructure.pg;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Comment;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackStatus;
import ru.itmo.cs.app.interviewing.feedback.domain.value.Grade;
import ru.itmo.cs.app.interviewing.feedback.infrastructure.pg.mapper.FeedbackRowMapper;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PgFeedbackRepositoryTest {

    private PgFeedbackRepository repository;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private FeedbackRowMapper rowMapper;

    @Mock
    private StoredDomainEventRepository storedDomainEventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new PgFeedbackRepository(jdbcOperations, rowMapper, storedDomainEventRepository);
    }

    @Test
    public void testFindById() {
        FeedbackId feedbackId = FeedbackId.generate();
        Feedback mockFeedback = mock(Feedback.class);

        when(jdbcOperations.query(anyString(), Mockito.any(MapSqlParameterSource.class), Mockito.eq(rowMapper)))
                .thenReturn(List.of(mockFeedback));

        Feedback result = repository.findById(feedbackId);

        assertNotNull(result);
        assertEquals(mockFeedback, result);
    }

    @Test
    public void testFindById_NotFound() {
        FeedbackId feedbackId = FeedbackId.generate();

        when(jdbcOperations.query(anyString(), Mockito.any(MapSqlParameterSource.class), Mockito.eq(rowMapper)))
                .thenReturn(Collections.emptyList());

        assertThrows(Exception.class, () -> repository.findById(feedbackId));
    }

    @Test
    public void testFindAll() {
        Feedback mockFeedback = mock(Feedback.class);

        when(jdbcOperations.query(anyString(), Mockito.eq(rowMapper)))
                .thenReturn(List.of(mockFeedback));

        List<Feedback> results = repository.findAll();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockFeedback, results.get(0));
    }

    @Test
    public void testSave_NewFeedback() {
        Feedback mockFeedback = mock(Feedback.class);
        FeedbackCreatedEvent event = mock(FeedbackCreatedEvent.class);

        when(mockFeedback.releaseEvents()).thenReturn(List.of(event));
        when(mockFeedback.getId()).thenReturn(FeedbackId.generate());
        when(mockFeedback.getCreatedAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:00:00").toInstant());
        when(mockFeedback.getUpdatedAt()).thenReturn(Timestamp.valueOf("2023-10-02 00:00:00").toInstant());
        when(mockFeedback.getInterviewId()).thenReturn(InterviewId.generate());
        when(mockFeedback.getStatus()).thenReturn(FeedbackStatus.SUBMITTED);
        when(mockFeedback.getGrade()).thenReturn(Grade.of(5));
        when(mockFeedback.getComment()).thenReturn(Comment.of("Good"));

        repository.save(mockFeedback);

        Mockito.verify(jdbcOperations).update(anyString(), Mockito.any(MapSqlParameterSource.class));
        Mockito.verify(storedDomainEventRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testSave_ExistingFeedback() {
        Feedback mockFeedback = mock(Feedback.class);

        when(mockFeedback.releaseEvents()).thenReturn(Collections.emptyList());
        when(mockFeedback.getId()).thenReturn(FeedbackId.generate());
        when(mockFeedback.getCreatedAt()).thenReturn(Timestamp.valueOf("2023-10-01 00:00:00").toInstant());
        when(mockFeedback.getUpdatedAt()).thenReturn(Timestamp.valueOf("2023-10-02 00:00:00").toInstant());
        when(mockFeedback.getInterviewId()).thenReturn(InterviewId.generate());
        when(mockFeedback.getStatus()).thenReturn(FeedbackStatus.SUBMITTED);
        when(mockFeedback.getGrade()).thenReturn(Grade.of(5));
        when(mockFeedback.getComment()).thenReturn(Comment.of("Good"));

        repository.save(mockFeedback);

        Mockito.verify(jdbcOperations).update(anyString(), Mockito.any(MapSqlParameterSource.class));
    }
}