package ru.ifmo.cs.interviewers.infrastructure.pg;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.interviewers.application.query.InterviewerUniqueIdentifiersQueryService;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;
import ru.ifmo.cs.interviewers.domain.value.InterviewerId;
import ru.ifmo.cs.interviewers.infrastructure.pg.mapper.InterviewerUniqueIdentifiersRowMapper;

@Primary
@Service
@AllArgsConstructor
public class PgInterviewerUniqueIdentifiersQueryService implements InterviewerUniqueIdentifiersQueryService {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final InterviewerUniqueIdentifiersRowMapper rowMapper;

    private final static String FIND_BY_ID = """
            select id, user_id from interviewers
            where id=:id
            """;

    private final static String FIND_BY_USER_ID = """
            select id, user_id from interviewers
            where user_id=:userId
            """;

    @Override
    public InterviewerUniqueIdentifiersDto findBy(UserId userId) {
        return jdbcOperations.query(
                        FIND_BY_USER_ID,
                        new MapSqlParameterSource().addValue("userId", userId.getUid()),
                        rowMapper)
                .stream()
                .findAny()
                .orElseThrow();
    }

    @Override
    public InterviewerUniqueIdentifiersDto findBy(InterviewerId interviewerId) {
        return jdbcOperations.query(
                        FIND_BY_ID,
                        new MapSqlParameterSource().addValue("id", interviewerId.value().toString()),
                        rowMapper)
                .stream()
                .findAny()
                .orElseThrow();
    }
}
