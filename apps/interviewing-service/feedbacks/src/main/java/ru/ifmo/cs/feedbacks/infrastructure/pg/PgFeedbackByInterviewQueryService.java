package ru.ifmo.cs.feedbacks.infrastructure.pg;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.feedbacks.application.query.FeedbackByInterviewQueryService;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

@Primary
@Service
@AllArgsConstructor
public class PgFeedbackByInterviewQueryService implements FeedbackByInterviewQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final FeedbackRowMapper rowMapper;

    private final static String FIND_BY_INTERVIEW_ID = """
            select * from feedbacks
            where interview_id=:interviewId
            """;

    @Override
    public Optional<Feedback> findByInterviewId(String interviewId) {
        return jdbcOperations.query(
                        FIND_BY_INTERVIEW_ID,
                        new MapSqlParameterSource().addValue("interviewId", interviewId),
                        rowMapper)
                .stream()
                .findAny();
    }

}
