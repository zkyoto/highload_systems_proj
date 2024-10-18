package ru.itmo.cs.app.interviewing.interview.infrastructure.in_memory;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.cs.app.interviewing.interview.application.query.InterviewPageQueryService;
import ru.itmo.cs.app.interviewing.interview.application.query.dto.InterviewPage;
import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;

@Service
@AllArgsConstructor
public class InMemoryStubInterviewPageQueryService implements InterviewPageQueryService {
    private final InterviewRepository interviewRepository;

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
