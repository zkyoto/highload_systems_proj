package ru.ifmo.cs.feedbacks.infrastructure.pg;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.application.query.FeedbacksPendingResultQueryService;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.domain.value.FeedbackStatus;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

@Primary
@Service
@AllArgsConstructor
public class PgFeedbacksPendingResultQueryService implements FeedbacksPendingResultQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final FeedbackRowMapper rowMapper;

    private final String QUERY = """
            select f.* from feedbacks f
            left join interview_results ir on f.id = ir.feedback_id
            where f.status = :status and ir.id is null
            """;

    @Override
    public List<Feedback> findAll() {
        return jdbcOperations.query(
                QUERY,
                new MapSqlParameterSource().addValue("status", FeedbackStatus.SUBMITTED.value()),
                rowMapper
        );
    }
}
