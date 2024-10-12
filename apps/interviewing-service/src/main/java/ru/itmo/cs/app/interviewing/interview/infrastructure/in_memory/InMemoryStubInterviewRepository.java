package ru.itmo.cs.app.interviewing.interview.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import ru.itmo.cs.app.interviewing.interview.domain.Interview;
import ru.itmo.cs.app.interviewing.interview.domain.InterviewRepository;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewEvent;
import ru.itmo.cs.app.interviewing.interview.domain.event.InterviewScheduledEvent;
import ru.itmo.cs.app.interviewing.interview.domain.value.InterviewId;

public class InMemoryStubInterviewRepository implements InterviewRepository {
    private final List<Interview> stubRepository = new LinkedList<>();
    @Override
    public Interview findById(InterviewId id) {
        return stubRepository.stream()
                             .filter(interview -> interview.getId().equals(id))
                             .findAny()
                             .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Interview> findAll() {
        return List.copyOf(stubRepository);
    }

    @Override
    public void save(Interview interview) {
        List<InterviewEvent> events = interview.releaseEvents();
        boolean isNew = events.stream().anyMatch(event -> event instanceof InterviewScheduledEvent);
        if (isNew) {
            insert(interview);
        } else {
            update(interview);
        }
    }

    private void insert(Interview interview) {
        stubRepository.add(interview);
    }

    private void update(Interview interview) {
        boolean entityForUpdateExists = stubRepository.removeIf(entity -> entity.getId().equals(interview.getId()));
        if (!entityForUpdateExists) {
            throw new IllegalStateException();
        }
        stubRepository.add(interview);
    }
}
