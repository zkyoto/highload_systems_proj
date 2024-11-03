package ru.ifmo.cs.domain_event.presentation.job;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import ru.ifmo.cs.domain_event.presentation.task.ProcessDomainEventsTask;

@AllArgsConstructor
public class ProcessDomainEventsTaskScheduler {
    private final ProcessDomainEventsTask processDomainEventsTask;

    @Scheduled(cron = "* * * * * *")
    public void runProcessDomainEventsTask() {
        processDomainEventsTask.execute();
    }

}

