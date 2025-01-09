package ru.ifmo.cs.interviewers.infrastructure.pg.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewerUniqueIdentifiersDto;

@Component
public class InterviewerUniqueIdentifiersRowMapper implements RowMapper<InterviewerUniqueIdentifiersDto> {

    @Override
    public InterviewerUniqueIdentifiersDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return InterviewerUniqueIdentifiersDto.hydrate(rs.getString("id"), rs.getLong("user_id"));
    }

}
