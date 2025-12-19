package sn.ndiaye.jobScheduler.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sn.ndiaye.jobScheduler.entities.Job;
import sn.ndiaye.jobScheduler.entities.JobExecution;
import sn.ndiaye.jobScheduler.repositories.JobExecutionRepository;
import sn.ndiaye.jobScheduler.repositories.JobRepository;
import sn.ndiaye.jobScheduler.repositories.specifications.JobSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class JobService{
    private final JobRepository jobRepository;
    private final JobExecutionRepository jobExecutionRepository;


    public void createJob(String name, boolean isEnabled, Integer frequencyInMinutes) {
        var job = new Job(name, isEnabled, frequencyInMinutes);
        if (isEnabled)
            synchronizeJob(job, frequencyInMinutes);
        jobRepository.save(job);
    }

    public void listJobs(Long id, String name, Integer frequencyInMinutes, LocalDate lastRunAt) {
        // Build query specifications
        Specification<Job> spec = Specification.unrestricted();
        if (id != null)
            spec = spec.and(JobSpec.hasId(id));
        if (name != null)
            spec = spec.and(JobSpec.hasName(name));
        if (frequencyInMinutes != null)
            spec = spec.and(JobSpec.hasFrequency(frequencyInMinutes));
        if (lastRunAt != null)
            spec = spec.and(JobSpec.hasLastRunAt(lastRunAt));

        // Fetch products by specification and print them
        jobRepository.findAll(spec).forEach(System.out::println);
    }

    public void listAllJobs() {
        var sort = Sort.by("lastRunAt")
                .and(Sort.by("name"));
        var jobs = jobRepository.findAll(sort);
        jobs.forEach(System.out::println);
    }

    List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void updateJob(Long jobId, String name, boolean isEnabled, Integer frequencyInMinutes) {
        var job = jobRepository.findById(jobId).orElseThrow();
        boolean hasNewName = name != null && !job.getName().equals(name);
        boolean hasNewEnableStatus = job.isEnabled() != isEnabled;
        boolean hasNewFrequency = frequencyInMinutes != null
                && job.getFrequencyInMinutes() != frequencyInMinutes.intValue();

        if (hasNewName)
            job.setName(name);

        if (hasNewFrequency) {
            job.setFrequencyInMinutes(frequencyInMinutes);
            synchronizeJob(job, frequencyInMinutes);
        }
        if (hasNewEnableStatus) {
            job.setEnabled(isEnabled);
            synchronizeJob(job, job.getFrequencyInMinutes());
        }

    }

    private void synchronizeJob(Job job, Integer frequencyInMinutes) {
        // When the job is disabled it doesn't have a run date anymore
        if (!job.isEnabled()) {
            job.setNextRunAt(null);
            return;
        }

        // Setup nextRun for never started now enabled jobs
        if (job.getLastRunAt() == null) {
            job.setNextRunAt(LocalDateTime.now().plusMinutes(frequencyInMinutes));
            return;
        }

        // Resume the nextRunAt date from the last run unless it's already outdated
        var nextRun = job.getLastRunAt().plusMinutes(frequencyInMinutes);
        if (nextRun.isBefore(LocalDateTime.now()))
            nextRun = LocalDateTime.now().plusMinutes(frequencyInMinutes);
        job.setNextRunAt(nextRun);
    }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    public void deleteAllJobs() {
        for (var job : jobRepository.findAll())
            jobExecutionRepository.deleteByJob(job);
        jobRepository.deleteAll();
    }

    public void executeJob(Long jobId) {
        var job = jobRepository.findById(jobId).orElseThrow();
        var jobExecution = JobExecution.builder()
                .startedAt(job.getNextRunAt())
                .finishedAt(job.getNextRunAt().plusMinutes(job.getFrequencyInMinutes()))
                .status(JobExecution.Status.SUCCESS)
                .message(String.format("job %s executed with %s", job.getName(), JobExecution.Status.SUCCESS))
                .job(job)
                .build();
        jobExecutionRepository.save(jobExecution);
        job.setLastRunAt(jobExecution.getFinishedAt());
        synchronizeJob(job, job.getFrequencyInMinutes());
        System.out.println("Executed " + job);
    }

    public void listAllJobExecutions() {
        var jobExecutions = jobExecutionRepository.findAll();
        jobExecutions.forEach(System.out::println);
    }
}
