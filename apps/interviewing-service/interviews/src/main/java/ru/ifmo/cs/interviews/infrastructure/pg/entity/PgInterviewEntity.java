package ru.ifmo.cs.interviews.infrastructure.pg.entity;

import java.sql.Timestamp;

public record PgInterviewEntity(
        String id,
        Timestamp createdAt,
        Timestamp updated_at,
        String interviewerId,
        String candidateId
) {}
