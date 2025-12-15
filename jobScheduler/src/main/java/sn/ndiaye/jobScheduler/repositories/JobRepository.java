package sn.ndiaye.jobScheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import sn.ndiaye.jobScheduler.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
