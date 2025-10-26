package az.vtb.iticket.model.scheduler;

import az.vtb.iticket.service.concrete.EventServiceHandler;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final EventServiceHandler eventServiceHandler;

    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(name = "markCompletedEventsAsDeleted", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void markCompletedEventsAsDeleted() {
        eventServiceHandler.markCompletedEventsAsDeleted();
    }
}
