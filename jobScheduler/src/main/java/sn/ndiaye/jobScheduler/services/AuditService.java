package sn.ndiaye.jobScheduler.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sn.ndiaye.jobScheduler.entities.Audit;
import sn.ndiaye.jobScheduler.entities.JobAction;
import sn.ndiaye.jobScheduler.repositories.AuditRepository;
import sn.ndiaye.jobScheduler.repositories.specifications.AuditSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuditService {
    private AuditRepository auditRepository;

    public void log(JobAction action, Long jobId, LocalDateTime occurredAt) {
        var audit = Audit.builder()
                .action(action)
                .jobId(jobId)
                .occurredAt(occurredAt)
                .build();
        auditRepository.save(audit);
    }

    public void seeFilteredReports(Long jobId, JobAction action, LocalDate occurredAt) {
        Specification<Audit> spec = Specification.unrestricted();
        if (jobId != null)
            spec = spec.and(AuditSpec.hasJobId(jobId));
        if (action != null)
            spec = spec.and(AuditSpec.hasAction(action));
        if (occurredAt != null)
            spec = spec.and(AuditSpec.hasOccurredAt(occurredAt));

        Sort sort = Sort.by("occurredAt");
        auditRepository.findAll(spec, sort)
                .forEach(System.out::println);
    }

    public void seeAllReports() {
        var sort = Sort.by("occurredAt");
         auditRepository.findAll(sort).forEach(System.out::println);
    }
}
