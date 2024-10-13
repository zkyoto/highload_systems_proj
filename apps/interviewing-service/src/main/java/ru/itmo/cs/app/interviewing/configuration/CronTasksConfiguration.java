package ru.itmo.cs.app.interviewing.configuration;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itmo.cs.app.interviewing.candidate.presentation.task.ProcessCandidateEventsTask;
import ru.itmo.cs.app.interviewing.feedback.presentation.task.ProcessFeedbackEventsTask;
import ru.itmo.cs.app.interviewing.interview.presentation.task.ProcessInterviewEventsTask;
import ru.itmo.cs.app.interviewing.interview_result.presentation.task.ProcessInterviewResultEventsTask;
import ru.itmo.cs.app.interviewing.interviewer.presentation.task.ProcessInterviewerEventsTask;

@Component
@AllArgsConstructor
public class CronTasksConfiguration {
    private final ProcessInterviewerEventsTask processInterviewerEventsTask;
    private final ProcessInterviewEventsTask processInterviewTask;
    private final ProcessCandidateEventsTask processCandidateEventsTask;
    private final ProcessFeedbackEventsTask processFeedbackEventsTask;
    private final ProcessInterviewResultEventsTask processInterviewResultEventsTask;

    @Scheduled(cron = "0 * * * * *")
    public void runProcessInterviewerEventsTask() {
        processInterviewerEventsTask.execute();
    }

    @Scheduled(cron = "0 * * * * *")
    public void runProcessInterviewTask() {
        processInterviewTask.execute();
    }

    @Scheduled(cron = "0 * * * * *")
    public void runProcessCandidateEventsTask() {
        processCandidateEventsTask.execute();
    }

    @Scheduled(cron = "0 * * * * *")
    public void runProcessFeedbackEventsTask() {
        processFeedbackEventsTask.execute();
    }

    @Scheduled(cron = "0 * * * * *")
    public void runProcessInterviewResultEventsTask() {
        processInterviewResultEventsTask.execute();
    }
}
