package ru.ifmo.cs.interviewers.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviewers.application.query.InterviewersPageQueryService;
import ru.ifmo.cs.interviewers.application.query.dto.InterviewersPage;
import ru.ifmo.cs.interviewers.domain.Interviewer;
import ru.ifmo.cs.interviewers.domain.InterviewerRepository;

@Service
public class InMemoryStubInterviewersPageQueryService implements InterviewersPageQueryService {
    private final InterviewerRepository interviewerRepository;

    public InMemoryStubInterviewersPageQueryService(
            @Qualifier("inMemoryStubInterviewerRepository") InterviewerRepository interviewerRepository
    ) {
        this.interviewerRepository = interviewerRepository;
    }

    @Override
    public InterviewersPage findFor(int page, int size) {
        List<Interviewer> allInterviewers = interviewerRepository.findAll();
        int from = page * size;
        int to = Math.min(from + size, allInterviewers.size());
        List<Interviewer> content = allInterviewers.subList(from, to);
        return new InterviewersPage(content,
                                    page,
                                    size,
                                    allInterviewers.size());
    }
}
