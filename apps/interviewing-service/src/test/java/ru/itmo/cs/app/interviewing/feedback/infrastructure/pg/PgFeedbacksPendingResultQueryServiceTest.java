package ru.itmo.cs.app.interviewing.feedback.infrastructure.pg;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.infrastructure.pg.mapper.FeedbackRowMapper;

public class PgFeedbacksPendingResultQueryServiceTest {

    private PgFeedbacksPendingResultQueryService service;

    @Mock
    private NamedParameterJdbcOperations jdbcOperations;

    @Mock
    private FeedbackRowMapper rowMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PgFeedbacksPendingResultQueryService(jdbcOperations, rowMapper);
    }

    @Test
    public void testFindAll() {
        Feedback mockFeedback = Mockito.mock(Feedback.class);  // Замените на ваш способ создания тестового объекта Feedback

        when(jdbcOperations.query(any(String.class), any(MapSqlParameterSource.class), eq(rowMapper)))
                .thenReturn(List.of(mockFeedback));

        List<Feedback> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(mockFeedback, result.get(0));
    }

    @Test
    public void testFindAll_NoResults() {
        when(jdbcOperations.query(any(String.class), any(MapSqlParameterSource.class), eq(rowMapper)))
                .thenReturn(List.of());

        List<Feedback> result = service.findAll();

        assertEquals(0, result.size());
    }
}