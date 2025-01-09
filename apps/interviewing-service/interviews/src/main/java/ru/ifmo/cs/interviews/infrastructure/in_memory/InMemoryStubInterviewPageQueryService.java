package ru.ifmo.cs.interviews.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.application.query.InterviewPageQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewPage;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;

@Service
public class InMemoryStubInterviewPageQueryService implements InterviewPageQueryService {
    private final InterviewRepository interviewRepository;

    public InMemoryStubInterviewPageQueryService(
            @Qualifier("inMemoryStubInterviewRepository") InterviewRepository interviewRepository
    ) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewPage findFor(int page, int size) {
        List<Interview> allInterviews = interviewRepository.findAll();
        int from = page * size;
        int to = Math.min(from + size, allInterviews.size());
        List<Interview> content = allInterviews.subList(from, to);
        return new InterviewPage(content,
                                 page,
                                 size,
                                 allInterviews.size());
    }
}
