package ru.itmo.cs.app.interviewing.candidate.infrastructure.pg.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itmo.cs.app.interviewing.candidate.domain.Candidate;

@Component
public class CandidateRowMapper implements RowMapper<Candidate> {
    @Override
    public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Candidate.hydrate(
                rs.getString("id"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant(),
                rs.getString("status"),
                rs.getString("full_name"));
    }
}
