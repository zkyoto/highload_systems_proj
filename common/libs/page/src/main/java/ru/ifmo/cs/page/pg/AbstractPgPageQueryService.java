package ru.ifmo.cs.page.pg;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractPgPageQueryService {

    public static class CountTotalRowMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("cnt");
        }
    }

}
