package sn.ndiaye.jobScheduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audits")
public class Audit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private JobAction action;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "occurred_at")
    private LocalDateTime occurredAt;

}
