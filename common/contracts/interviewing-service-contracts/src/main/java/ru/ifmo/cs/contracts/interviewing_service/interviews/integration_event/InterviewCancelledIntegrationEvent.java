package ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.ifmo.cs.integration_event.IntegrationEvent;

@Value
public class InterviewCancelledIntegrationEvent implements IntegrationEvent {
    public static final String EVENT_TYPE = "interview_cancelled_integration_event";
    @JsonProperty("event_type") String eventType;
    @JsonProperty("deduplication_key") String deduplicationKey;
    @JsonProperty("occurred_on") Instant occurredOn;
    @JsonProperty("interview_id") String interviewId;
    @JsonProperty("interviewer_id") String interviewerId;
    @JsonProperty("candidate_id") String candidateId;

    @JsonCreator
    public InterviewCancelledIntegrationEvent(
            @JsonProperty("deduplication_key") String deduplicationKey,
            @JsonProperty("occurred_on") Instant occurredOn,
            @JsonProperty("interview_id") String interviewId,
            @JsonProperty("interviewer_id")String interviewerId,
            @JsonProperty("candidate_id")String candidateId
    ) {
        this.deduplicationKey = deduplicationKey;
        this.occurredOn = occurredOn;
        this.interviewId = interviewId;
        this.interviewerId = interviewerId;
        this.candidateId = candidateId;
        this.eventType = EVENT_TYPE;
    }
}
