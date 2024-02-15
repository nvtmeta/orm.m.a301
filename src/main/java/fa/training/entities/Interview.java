package fa.training.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Interview")
@Table(name = "Interview", schema = "training")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", nullable = false)
    private Integer id;

    @Column(name = "time", columnDefinition = "varchar(255)", nullable = false)
    private String time;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "interviewer", columnDefinition = "varchar(255)", nullable = false)
    private String interviewer;

    @Column(name = "comments", columnDefinition = "varchar(2000)", nullable = false)
    private String comments;

    @Column(name = "interview_result", columnDefinition = "varchar(255)", nullable = false)
    private String interviewResult;

    @Column(name = "remark", columnDefinition = "varchar(1000)", nullable = false)
    private String remark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
