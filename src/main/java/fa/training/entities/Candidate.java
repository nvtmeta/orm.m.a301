package fa.training.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Candidate")
@Table(name = "Candidate", schema = "training")
@Check(constraints = "level between 1 and 7 and gender between 0 and 1")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id", nullable = false)
    private Integer id;

    @Column(name = "full_name", columnDefinition = "varchar(255)", nullable = false)
    @NotNull
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    //    gender: accepts value 0 (female) and 1 (male) only.
    @Column(name = "gender", nullable = false, columnDefinition = "int range 0 to 1")
    @Size(min = 0, max = 1)
    private int gender;

    @Column(name = "graduation_year", nullable = false)
    @NotNull
    private LocalDate graduationYear;

    @Column(name = "phone", columnDefinition = "varchar(255)", nullable = false, unique = true)
    @UniqueElements
    @NotNull
    private String phone;

    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
    @UniqueElements
    @NotNull
    private String email;

    @Column(name = "skill", columnDefinition = "varchar(255)", nullable = false)
    private String skill;

    @Column(name = "foreign_language", columnDefinition = "varchar(255)", nullable = false)
    private String foreignLanguage;

    // level: skill level of candidate (accepts value range from 1 to 7 only)
    @Column(name = "level", columnDefinition = "int", nullable = false)
    @Min(value = 1)
    @Max(value = 7)
    private int level;

    @Column(name = "cv", columnDefinition = "varchar(255)", nullable = false)
    private String cv;

    @Column(name = "allocation_status", columnDefinition = "int", nullable = false)
    private Integer allocationStatus;

    @Column(name = "remark", columnDefinition = "varchar(1000)", nullable = false)
    private String remark;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Interview> interviews;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<EntryTest> entryTests;


}
