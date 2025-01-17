package ru.ifmo.cs.contracts.interviewing_service.candidates.integration_event;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.ifmo.cs.contracts.interviewing_service.interviews.integration_event.InterviewScheduledIntegrationEvent;
import ru.ifmo.cs.integration_event.IntegrationEvent;

@Value
public class CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent implements IntegrationEvent {
    public static final String EVENT_TYPE = "candidate_status_transfer_to_waiting_for_interview_fail_integration_event";
    @JsonProperty("event_type")
    String eventType;
    @JsonProperty("deduplication_key")
    String deduplicationKey;
    @JsonProperty("occurred_on")
    Instant occurredOn;
    @JsonProperty("interview_id")
    String interviewId;
    @JsonProperty("interviewer_id")
    String interviewerId;
    @JsonProperty("candidate_id")
    String candidateId;

    @JsonCreator
    public CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent(
            @JsonProperty("deduplication_key") String deduplicationKey,
            @JsonProperty("occurred_on") Instant occurredOn,
            @JsonProperty("interview_id") String interviewId,
            @JsonProperty("interviewer_id") String interviewerId,
            @JsonProperty("candidate_id") String candidateId
    ) {
        this.deduplicationKey = deduplicationKey;
        this.occurredOn = occurredOn;
        this.interviewId = interviewId;
        this.interviewerId = interviewerId;
        this.candidateId = candidateId;
        this.eventType = EVENT_TYPE;
    }

    public static CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent createFrom(
            InterviewScheduledIntegrationEvent interviewScheduledIntegrationEvent
    ) {
        Instant now = Instant.now();
        return new CandidateStatusTransferToWaitingForInterviewFailIntegrationEvent(
                now.toString(),
                now,
                interviewScheduledIntegrationEvent.getInterviewId(),
                interviewScheduledIntegrationEvent.getInterviewerId(),
                interviewScheduledIntegrationEvent.getCandidateId()
        );
    }
}
