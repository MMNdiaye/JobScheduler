package sn.ndiaye.jobScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import sn.ndiaye.jobScheduler.entities.Job;
import sn.ndiaye.jobScheduler.services.JobScheduler;
import sn.ndiaye.jobScheduler.services.JobService;

import java.time.LocalDate;

@SpringBootApplication
public class JobSchedulerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JobSchedulerApplication.class, args);
        var jobService = context.getBean(JobService.class);
        jobService.listAllJobs();

    }

}
