package fa.training.dao;

import fa.training.dao.impl.EntryTestDaoImpl;
import fa.training.dao.impl.InterviewDaoImpl;
import fa.training.entities.Candidate;
import fa.training.entities.Interview;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class InterviewDaoTest {


    static InterviewDao interviewDao;


    @BeforeAll
    public static void setUp() {
        interviewDao = new InterviewDaoImpl();
    }

    @Test
    public void testSave() {
        Interview interview = new Interview().builder()
                .date(LocalDate.now())
                .interviewer("test")
                .interviewResult("test")
                .remark("test")
                .time("test")
                .comments("test")
                .build();
        Candidate candidate  = Candidate.builder()
                .foreignLanguage("test")
                .level(1)
                .entryTests(null)
                .dateOfBirth(LocalDate.now())
                .fullName("Mark")
                .gender(1)
                .graduationYear(LocalDate.now())
                .phone("34565867")
                .skill("test")
                .cv("Mark.cv")
                .remark("test")
                .allocationStatus(1)
                .email("Mark@gmail.com")
                .build();

        interview.setCandidate(candidate);

        interviewDao.save(interview);

    }
}
