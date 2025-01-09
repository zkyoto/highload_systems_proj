package ru.ifmo.cs.feedbacks.infrastructure.pg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.ifmo.cs.feedbacks.application.query.dto.FeedbackPage;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

import java.util.List;

public class PgFeedbackPageQueryServiceTest {

    private PgFeedbackPageQueryService service;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private FeedbackRowMapper feedbackRowMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PgFeedbackPageQueryService(jdbcOperations, feedbackRowMapper);
    }

    @Test
    public void testFindFor() {
        int page = 0;
        int size = 10;
        long countTotal = 100L;
        Feedback mockFeedback = Mockito.mock(Feedback.class);

        when(jdbcOperations.query(anyString(), any(PgFeedbackPageQueryService.CountTotalRowMapper.class)))
                .thenReturn(List.of(countTotal));

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(feedbackRowMapper)))
                .thenReturn(List.of(mockFeedback));

        FeedbackPage result = service.findFor(page, size);

        assertEquals(countTotal, result.totalElements());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(1, result.content().size());
        assertEquals(mockFeedback, result.content().get(0));
    }

    @Test
    public void testFindFor_EmptyResults() {
        int page = 0;
        int size = 10;
        long countTotal = 0L;

        when(jdbcOperations.query(anyString(), any(PgFeedbackPageQueryService.CountTotalRowMapper.class)))
                .thenReturn(List.of(countTotal));

        when(jdbcOperations.query(anyString(), any(MapSqlParameterSource.class), eq(feedbackRowMapper)))
                .thenReturn(List.of());

        FeedbackPage result = service.findFor(page, size);

        assertEquals(countTotal, result.totalElements());
        assertEquals(page, result.pageNumber());
        assertEquals(size, result.pageSize());
        assertEquals(0, result.content().size());
    }
}