package ru.ifmo.cs.feedbacks.infrastructure.pg;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.page.pg.AbstractPgPageQueryService;
import ru.ifmo.cs.feedbacks.application.query.FeedbackPageQueryService;
import ru.ifmo.cs.feedbacks.application.query.dto.FeedbackPage;
import ru.ifmo.cs.feedbacks.domain.Feedback;
import ru.ifmo.cs.feedbacks.infrastructure.pg.mapper.FeedbackRowMapper;

@Primary
@Service
@AllArgsConstructor
public class PgFeedbackPageQueryService extends AbstractPgPageQueryService implements FeedbackPageQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final FeedbackRowMapper feedbackRowMapper;

    private static final String QUERY = """
            select *
            from feedbacks
            order by created_at desc
            limit :limit
            offset :offset
            """;

    private static final String COUNT_TOTAL = """
            select count(*) as cnt from feedbacks
            """;

    @Override
    public FeedbackPage findFor(int page, int size) {
        Long countTotal =
                jdbcOperations.query(COUNT_TOTAL, new CountTotalRowMapper())
                        .stream()
                        .findAny()
                        .orElseThrow();

        List<Feedback> feedbacksPage = jdbcOperations.query(
                        QUERY,
                        new MapSqlParameterSource().addValue("limit", size)
                                .addValue("offset", page * size),
                        feedbackRowMapper)
                .stream()
                .toList();
        return new FeedbackPage(feedbacksPage, page, size, countTotal);
    }

}
