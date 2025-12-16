package sn.ndiaye.jobScheduler.entities;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name  = "jobs")
public class Job {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "enabled")
    private boolean isEnabled;

    @NonNull
    @Column(name = "frequency_in_mins")
    private Integer frequencyInMinutes;

    @Column(name = "last_run_at")
    private LocalDateTime lastRunAt = null;

    @Column(name = "next_run_at")
    private LocalDateTime nextRunAt = null;


}
