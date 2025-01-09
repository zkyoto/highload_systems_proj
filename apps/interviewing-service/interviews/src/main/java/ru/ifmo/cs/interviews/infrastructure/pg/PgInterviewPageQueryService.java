package ru.ifmo.cs.interviews.infrastructure.pg;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.application.query.InterviewPageQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewPage;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgInterviewEntity;
import ru.ifmo.cs.interviews.infrastructure.pg.mapper.PgInterviewEntityRowMapper;
import ru.ifmo.cs.page.pg.AbstractPgPageQueryService;

@Primary
@Service
@AllArgsConstructor
public class PgInterviewPageQueryService extends AbstractPgPageQueryService implements InterviewPageQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final PgScheduleEntityDao pgScheduleEntityDao;
    private final PgInterviewEntityRowMapper pgInterviewEntityRowMapper;

    private static final String QUERY = """
            select *
            from interviews
            order by created_at desc
            limit :limit
            offset :offset
            """;

    private static final String COUNT_TOTAL = """
            select count(*) as cnt from interviews
            """;

    @Override
    public InterviewPage findFor(int page, int size) {
        Long countTotal = jdbcOperations.query(COUNT_TOTAL, new CountTotalRowMapper()).stream().findAny().orElseThrow();

        List<PgInterviewEntity> pagedPgInterviewEntities = jdbcOperations.query(
                QUERY,
                new MapSqlParameterSource().addValue("limit", size)
                        .addValue("offset", page * size),
                pgInterviewEntityRowMapper).stream().toList();
        List<Interview> content = pagedPgInterviewEntities.stream()
                                                          .map(e -> Interview.hydrate(e, pgScheduleEntityDao.findFor(e)))
                                                          .toList();
        return new InterviewPage(content, page, size, countTotal);
    }

}
