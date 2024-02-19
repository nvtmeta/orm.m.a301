package fa.training.entities;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
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

    @Column(name = "date", columnDefinition = "date", nullable = false)
    private LocalDate date;

    @Column(name = "language_valuator", columnDefinition = "varchar(255)", nullable = false)
    private String languageValuator;

    @Column(name = "language_result", columnDefinition = "varchar(255)", nullable = false)
    @Min(0)
    @Max(10)
    @Check(constraints = "language_result IN (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)")
    private String languageResult;

    @Column(name = "technical_valuator", columnDefinition = "varchar(255)", nullable = false)
    private String technicalValuator;

    @Column(name = "technical_result", columnDefinition = "varchar(255)", nullable = false)
    @Check(constraints = "technical_result BETWEEN 0 AND 10")
    private String technicalResult;

    //    result: 'pass' or 'fail' value.
    @Column(name = "result", columnDefinition = "varchar(255)", nullable = false)
    @Pattern(regexp = "^(pass|fail)$", message = "Result must be either 'pass' or 'fail'")
    @Check(constraints = "result IN ('pass', 'fail')")
    private String result;

    @Column(name = "remark", columnDefinition = "varchar(1000)", nullable = false)
    private String remark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Column(name = "entry_test_ skill", columnDefinition = "varchar(255)", nullable = false)
    private String entryTestSkill;

}
