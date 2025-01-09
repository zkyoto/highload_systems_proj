package ru.ifmo.cs.interviewers.infrastructure.pg.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.interviewers.domain.Interviewer;

@Component
public class InterviewerRowMapper implements RowMapper<Interviewer> {

    @Override
    public Interviewer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Interviewer.hydrate(rs.getString("id"),
                                   rs.getLong("user_id"),
                                   rs.getString("full_name"),
                                   rs.getTimestamp("created_at").toInstant(),
                                   rs.getTimestamp("updated_at").toInstant(),
                                   rs.getString("status"));
    }

}
