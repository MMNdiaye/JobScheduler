package sn.ndiaye.jobScheduler.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import sn.ndiaye.jobScheduler.entities.Job;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@RequiredArgsConstructor
@Service
@Lazy
public class JobScheduler {
    private final JobService jobService;
    private Thread thread;

    @PostConstruct
    private void init() {
        thread = new Thread(this::run);
        thread.start();
    }

    private void run() {
            while (!thread.isInterrupted()) {
                var scheduledJobs = sortedByNextRunJobs(jobService.getAllJobs());
                observe(scheduledJobs);
            }
    }

    private List<Job> sortedByNextRunJobs(List<Job> jobs) {
        return jobService.getAllJobs().stream()
                .filter(Job::isEnabled)
                .sorted(Comparator.comparing(Job::getNextRunAt))
                .toList();
    }

    private void observe(List<Job> scheduledJobs){
        for (var job : scheduledJobs) {
            if (job.getNextRunAt().isBefore(LocalDateTime.now())) {
                jobService.executeJob(job.getId());
                return;
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
