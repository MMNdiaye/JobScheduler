package sn.ndiaye.jobScheduler.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import sn.ndiaye.jobScheduler.entities.Audit;
import sn.ndiaye.jobScheduler.entities.JobAction;

import java.time.LocalDate;

public class AuditSpec {
    public static Specification<Audit> hasJobId(Long jobId) {
        return (root, query, cb)
                -> cb.equal(root.get("jobId"), jobId);
    }

    public static Specification<Audit> hasAction(JobAction action) {
        return (root, query, cb)
                -> cb.equal(root.get("action"), action);
    }

    public static Specification<Audit> hasOccurredAt(LocalDate occurredAt) {
        return ((root, query, cb) ->  {
            var start = occurredAt.atStartOfDay();
            var end = occurredAt.plusDays(1).atStartOfDay();
            return cb.between(root.get("occurredAt"), start, end);
        });
    }

}
