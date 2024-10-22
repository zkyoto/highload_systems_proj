package ru.itmo.cs.app.interviewing.feedback.infrastructure.in_memory;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEvent;
import ru.ifmo.cs.domain_event.domain.stored_event.StoredDomainEventRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.Feedback;
import ru.itmo.cs.app.interviewing.feedback.domain.FeedbackRepository;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackCreatedEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.event.FeedbackEvent;
import ru.itmo.cs.app.interviewing.feedback.domain.value.FeedbackId;

@Repository
@AllArgsConstructor
public class InMemoryStubFeedbackRepository implements FeedbackRepository {
    private final StoredDomainEventRepository inMemoryStubStoredDomainEventRepository;
    private final List<Feedback> stubRepository = new LinkedList<>();

    @Override
    public Feedback findById(FeedbackId id) {
        return stubRepository.stream()
                             .filter(feedback -> feedback.getId().equals(id))
                             .findAny()
                             .orElseThrow();
    }

    @Override
    public List<Feedback> findAll() {
        return List.copyOf(stubRepository);
    }

    @Override
    public void save(Feedback feedback) {
        List<FeedbackEvent> releasedEvents = feedback.releaseEvents();
        boolean isNew = releasedEvents.stream().anyMatch(event -> event instanceof FeedbackCreatedEvent);
        if (isNew) {
            insert(feedback);
        } else {
            update(feedback);
        }

        releasedEvents.stream()
                .map(StoredDomainEvent::of)
                .forEach(inMemoryStubStoredDomainEventRepository::save);
    }

    private void insert(Feedback feedback) {
        stubRepository.add(feedback);
    }

    private void update(Feedback feedback) {
        boolean removed = stubRepository.removeIf(entity -> entity.getId().equals(feedback.getId()));
        if (!removed) {
            throw new IllegalStateException();
        }
        stubRepository.add(feedback);
    }
}
