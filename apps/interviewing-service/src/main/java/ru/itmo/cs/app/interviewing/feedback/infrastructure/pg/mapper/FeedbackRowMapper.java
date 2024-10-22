package ru.itmo.cs.app.interviewing.feedback.infrastructure.pg.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;

@Component
public class FeedbackRowMapper implements RowMapper<Feedback> {

    @Override
    public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer nullableGrade = rs.getInt("grade");
        if (rs.wasNull()) {
            nullableGrade = null;
        }
        return Feedback.hydrate(
                rs.getString("id"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant(),
                rs.getString("interview_id"),
                rs.getString("status"),
                nullableGrade,
                rs.getString("comment")
        );
    }

}
