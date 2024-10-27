package ru.itmo.cs.app.interviewing.interview.infrastructure.pg.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.Schedule;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

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
