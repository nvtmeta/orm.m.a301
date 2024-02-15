package fa.training.entities;


import jakarta.persistence.*;
import lombok.*;
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
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id", nullable = false)
    private Integer id;

    @Column(name = "full_name", columnDefinition = "varchar(255)", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    //    gender: accepts value 0 (female) and 1 (male) only.
    @Column(name = "gender", nullable = false)
    @Range(min = 0, max = 1)
    private Integer gender;

    @Column(name = "graduation_year", nullable = false)
    private LocalDate graduationYear;

    @Column(name = "phone", columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String email;

    //o level: skill level of candidate (accepts value range from 1 to 7 only)
    @Column(name = "skill", columnDefinition = "varchar(255)", nullable = false)
    @Range(min = 1, max = 7)
    private String skill;

    @Column(name = "foreign_language", columnDefinition = "varchar(255)", nullable = false)
    private String foreignLanguage;

    @Column(name = "level", columnDefinition = "int", nullable = false)
    private Integer level;

    @Column(name = "cv", columnDefinition = "varchar(255)", nullable = false)
    private String cv;

    @Column(name = "allocation_status", columnDefinition = "int", nullable = false)
    private Integer allocationStatus;

    @Column(name = "remark", columnDefinition = "varchar(1000)", nullable = false)
    private String remark;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Interview> interviews;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<EntryTest> entryTests;

}
