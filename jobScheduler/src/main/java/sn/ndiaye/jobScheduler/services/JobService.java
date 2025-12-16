package sn.ndiaye.jobScheduler.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.ndiaye.jobScheduler.entities.Job;
import sn.ndiaye.jobScheduler.repositories.JobRepository;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class JobService {
    private JobRepository jobRepository;

    public void createJob(String name, Boolean isEnabled, Integer frequencyInMinutes) {
        var job = new Job(name, isEnabled, frequencyInMinutes);
        if (isEnabled)
            job.setNextRunAt(LocalDateTime.now().plusMinutes(frequencyInMinutes));
        jobRepository.save(job);
    }

    public void listAllJobs() {
        var sort = Sort.by("lastRunAt")
                .and(Sort.by("name"));
        var jobs = jobRepository.findAll(sort);
        jobs.forEach(System.out::println);
    }

}
