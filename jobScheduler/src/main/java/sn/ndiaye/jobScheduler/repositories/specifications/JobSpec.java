package sn.ndiaye.jobScheduler.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.ndiaye.jobScheduler.entities.Job;

import java.time.LocalDate;

public class JobSpec {
    public static Specification<Job> hasId(Long id) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Job> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Job> hasFrequency(Integer frequencyInMins) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("frequencyInMinutes"), frequencyInMins);
    }

    public static Specification<Job> hasLastRunAt(LocalDate lastRunAt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastRunAt").as(LocalDate.class), lastRunAt);
    }
}
