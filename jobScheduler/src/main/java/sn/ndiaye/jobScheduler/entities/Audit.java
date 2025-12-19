package sn.ndiaye.jobScheduler.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "audits")
public class Audit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    private JobAction action;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "occurred_at")
    private LocalDateTime occurredAt;

}
