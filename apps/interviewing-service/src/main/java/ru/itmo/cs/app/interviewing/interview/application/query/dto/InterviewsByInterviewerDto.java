package ru.itmo.cs.app.interviewing.interview.application.query.dto;

import java.util.List;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public record InterviewsByInterviewerDto(InterviewerId interviewerId, List<Interview> interviews) {}
