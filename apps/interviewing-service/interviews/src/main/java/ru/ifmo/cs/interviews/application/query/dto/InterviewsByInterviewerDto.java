package ru.ifmo.cs.interviews.application.query.dto;

import java.util.List;

import ru.ifmo.cs.interviews.domain.Interview;

public record InterviewsByInterviewerDto(String interviewerId, List<Interview> interviews) {}
