package fa.training.entities;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EntryTest")
@Table(name = "EntryTest", schema = "training")
public class EntryTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", nullable = false)
    private Integer id;

    @Column(name = "time", columnDefinition = "varchar(255)", nullable = false)
    private String time;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "language_valuator", columnDefinition = "varchar(255)", nullable = false)
    private String languageValuator;

    @Range(min = 0, max = 10)
    @Column(name = "language_result", columnDefinition = "varchar(255)", nullable = false)
    private String languageResult;

    @Column(name = "technical_valuator", columnDefinition = "varchar(255)", nullable = false)
    private String technicalValuator;

    @Column(name = "technical_result", columnDefinition = "varchar(255)", nullable = false)
    @Range(min = 0, max = 10)
    private String technicalResult;

    //    result: 'pass' or 'fail' value.
    @Column(name = "result", columnDefinition = "varchar(255)", nullable = false)
    @Pattern(regexp = "^(pass|fail)$", message = "Result must be either 'pass' or 'fail'")
    private String result;

    @Column(name = "remark", columnDefinition = "varchar(1000)", nullable = false)
    private String remark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(name = "entry_test_ skill", columnDefinition = "varchar(255)", nullable = false)
    private String entryTestSkill;

}
