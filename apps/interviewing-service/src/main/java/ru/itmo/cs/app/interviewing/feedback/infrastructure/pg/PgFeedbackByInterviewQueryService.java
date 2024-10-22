package ru.itmo.cs.app.interviewing.feedback.infrastructure.pg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.feedback.application.query.FeedbackByInterviewQueryService;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.infrastructure.pg.mapper.FeedbackRowMapper;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

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
    public Optional<Feedback> findByInterviewId(InterviewId interviewId) {
        return jdbcOperations.query(
                        FIND_BY_INTERVIEW_ID,
                        new MapSqlParameterSource().addValue("interviewId", interviewId.value().toString()),
                        rowMapper)
                .stream()
                .findAny();
    }

}
