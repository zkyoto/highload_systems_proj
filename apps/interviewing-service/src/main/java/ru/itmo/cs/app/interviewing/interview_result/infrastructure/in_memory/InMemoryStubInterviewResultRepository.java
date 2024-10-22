package ru.itmo.cs.app.interviewing.interview_result.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResult;
import ru.itmo.cs.app.interviewing.interview_result.domain.InterviewResultRepository;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultCreatedEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.event.InterviewResultEvent;
import ru.itmo.cs.app.interviewing.interview_result.domain.value.InterviewResultId;

@Repository
@AllArgsConstructor
public class InMemoryStubInterviewResultRepository implements InterviewResultRepository {
    private final StoredDomainEventRepository inMemoryStubStoredDomainEventRepository;
    private final List<InterviewResult> stubRepository = new LinkedList<>();

    @Override
    public InterviewResult findById(InterviewResultId interviewResultId) {
        return stubRepository.stream()
                             .filter(e -> e.getId().equals(interviewResultId))
                             .findAny()
                             .orElseThrow();
    }

    @Override
    public List<InterviewResult> findAll() {
        return List.copyOf(stubRepository);
    }

    @Override
    public void save(InterviewResult interviewResult) {
        List<InterviewResultEvent> releasedEvents = interviewResult.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof InterviewResultCreatedEvent);
        if (isNew) {
            insert(interviewResult);
        } else {
            update(interviewResult);
        }

        releasedEvents.stream()
                .map(StoredDomainEvent::of)
                .forEach(inMemoryStubStoredDomainEventRepository::save);
    }

    private void insert(InterviewResult interviewResult) {
        stubRepository.add(interviewResult);
    }

    private void update(InterviewResult interviewResult) {
        boolean removed = stubRepository.removeIf(e -> e.getId().equals(interviewResult.getId()));
        if (!removed) {
            throw new IllegalStateException();
        }
        stubRepository.add(interviewResult);
    }

}
