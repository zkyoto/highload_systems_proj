package ru.ifmo.cs.interviews.infrastructure.pg.entity;

import java.sql.Timestamp;

import ru.ifmo.cs.interviews.domain.Schedule;
import ru.ifmo.cs.interviews.domain.value.InterviewId;

public record PgScheduleEntity(
        String id,
        Timestamp createdAt,
        Timestamp updatedAt,
        Timestamp scheduledFor,
        String status,
        String interviewId
) {
    public static PgScheduleEntity from(InterviewId interviewId, Schedule schedule) {
        return new PgScheduleEntity(schedule.getId().value().toString(),
                                    Timestamp.from(schedule.getCreatedAt()),
                                    Timestamp.from(schedule.getUpdatedAt()),
                                    Timestamp.from(schedule.getScheduledFor()),
                                    schedule.getStatus().value(),
                                    interviewId.value().toString());
    }
}
