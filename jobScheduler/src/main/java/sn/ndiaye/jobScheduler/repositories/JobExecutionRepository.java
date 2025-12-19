package sn.ndiaye.jobScheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ndiaye.jobScheduler.entities.Job;
import sn.ndiaye.jobScheduler.entities.JobExecution;

public interface JobExecutionRepository extends JpaRepository<JobExecution, Long> {
    void deleteByJob(Job job);
}
