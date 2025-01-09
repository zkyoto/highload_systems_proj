package ru.ifmo.cs.feedbacks.infrastructure.pg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

public class PgFeedbackByInterviewQueryServiceTest {

    private PgFeedbackByInterviewQueryService service;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private FeedbackRowMapper rowMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PgFeedbackByInterviewQueryService(jdbcOperations, rowMapper);
    }

    @Test
    public void testFindByInterviewId_Found() {
        Feedback mockFeedback = Mockito.mock(Feedback.class);

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), any(FeedbackRowMapper.class)))
                .thenReturn(List.of(mockFeedback));

        Optional<Feedback> result = service.findByInterviewId("interviewId");

        assertTrue(result.isPresent());
        assertEquals(mockFeedback, result.get());
    }

    @Test
    public void testFindByInterviewId_NotFound() {

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), any(FeedbackRowMapper.class)))
                .thenReturn(Collections.emptyList());

        Optional<Feedback> result = service.findByInterviewId("interviewId");

        assertFalse(result.isPresent());
    }
}