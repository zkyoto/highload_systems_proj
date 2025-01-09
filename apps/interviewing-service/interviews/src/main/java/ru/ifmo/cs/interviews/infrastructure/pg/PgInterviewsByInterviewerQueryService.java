package ru.ifmo.cs.interviews.infrastructure.pg;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.application.query.InterviewsByInterviewerQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewsByInterviewerDto;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.infrastructure.pg.entity.PgInterviewEntity;
import ru.ifmo.cs.interviews.infrastructure.pg.mapper.PgInterviewEntityRowMapper;

@Primary
@Service
@AllArgsConstructor
public class PgInterviewsByInterviewerQueryService implements InterviewsByInterviewerQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final PgInterviewEntityRowMapper pgInterviewEntityRowMapper;
    private final PgScheduleEntityDao pgScheduleEntityDao;

    private final static String QUERY = """
            select *
            from interviews
            where interviewer_id = :interviewerId
            order by created_at
            """;

    @Override
    public InterviewsByInterviewerDto findFor(String interviewerId) {
        List<PgInterviewEntity> interviewsByInterviewer = jdbcOperations.query(
                QUERY,
                new MapSqlParameterSource().addValue("interviewerId", interviewerId),
                pgInterviewEntityRowMapper
        );

        return new InterviewsByInterviewerDto(
                interviewerId,
                interviewsByInterviewer.stream().map(e -> Interview.hydrate(e, pgScheduleEntityDao.findFor(e))).toList()
        );
    }

}
