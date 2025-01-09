package ru.ifmo.cs.contracts.interviewing_service.interview_results.integration_event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.ifmo.cs.integration_event.IntegrationEvent;

@Value
public class InterviewResultCreatedIntegrationEvent implements IntegrationEvent {
    public static final String EVENT_TYPE = "interview_result_created_integration_event";
    @JsonProperty("event_type") String eventType;
    @JsonProperty("deduplication_key") String deduplicationKey;
    @JsonProperty("occurred_on") Instant occurredOn;
    @JsonProperty("interview_result_id") String interviewResultId;
    @JsonProperty("feedback_id") String feedbackId;

    @JsonCreator
    public InterviewResultCreatedIntegrationEvent(
            @JsonProperty("deduplication_key") String deduplicationKey,
            @JsonProperty("occurred_on") Instant occurredOn,
            @JsonProperty("interview_result_id") String interviewResultId,
            @JsonProperty("feedback_id") String feedbackId
    ) {
        this.deduplicationKey = deduplicationKey;
        this.occurredOn = occurredOn;
        this.interviewResultId = interviewResultId;
        this.feedbackId = feedbackId;
        this.eventType = EVENT_TYPE;
    }
}
