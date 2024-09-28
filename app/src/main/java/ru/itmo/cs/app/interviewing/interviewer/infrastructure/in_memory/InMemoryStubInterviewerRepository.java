package ru.itmo.cs.app.interviewing.interviewer.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import ru.itmo.cs.app.interviewing.interviewer.domain.Interviewer;
import ru.itmo.cs.app.interviewing.interviewer.domain.InterviewerRepository;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerCreatedEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.event.InterviewerEvent;
import ru.itmo.cs.app.interviewing.interviewer.domain.value.InterviewerId;

public class InMemoryStubInterviewerRepository implements InterviewerRepository {
    List<Interviewer> stubRepository = new LinkedList<>();

    @Override
    public Interviewer findById(InterviewerId id) {
        return stubRepository.stream()
                .filter(entity -> entity.getInterviewerId().equals(id))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void save(Interviewer interviewer) {
        List<InterviewerEvent> releasedEvents = interviewer.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof InterviewerCreatedEvent);
        if (isNew) {
            insert(interviewer);
        } else {
            update(interviewer);
        }
    }

    private void insert(Interviewer interviewer) {
        stubRepository.add(interviewer);
    }

    private void update(Interviewer interviewer) {
        boolean entityForUpdateExists = stubRepository.removeIf(entity -> entity.getInterviewerId().equals(interviewer.getInterviewerId()));
        if (!entityForUpdateExists) {
            throw new IllegalStateException();
        }
        stubRepository.add(interviewer);
    }
}
