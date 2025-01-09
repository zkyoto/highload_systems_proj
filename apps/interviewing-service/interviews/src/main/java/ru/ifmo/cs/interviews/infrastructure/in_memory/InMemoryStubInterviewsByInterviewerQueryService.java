package ru.ifmo.cs.interviews.infrastructure.in_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.cs.interviews.application.query.InterviewsByInterviewerQueryService;
import ru.ifmo.cs.interviews.application.query.dto.InterviewsByInterviewerDto;
import ru.ifmo.cs.interviews.domain.Interview;
import ru.ifmo.cs.interviews.domain.InterviewRepository;

@Service
public class InMemoryStubInterviewsByInterviewerQueryService implements InterviewsByInterviewerQueryService {
    private final InterviewRepository interviewRepository;

    public InMemoryStubInterviewsByInterviewerQueryService(
            @Qualifier("inMemoryStubInterviewRepository") InterviewRepository interviewRepository
    ) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewsByInterviewerDto findFor(String interviewerId) {
        List<Interview> interviewList = interviewRepository.findAll()
                                                           .stream()
                                                           .filter(interview -> interview.getInterviewerId()
                                                                                         .equals(interviewerId))
                                                           .toList();
        return new InterviewsByInterviewerDto(interviewerId, interviewList);
    }

}
