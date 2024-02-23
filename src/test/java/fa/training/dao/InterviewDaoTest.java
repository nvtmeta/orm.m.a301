package fa.training.dao;

import fa.training.dao.impl.EntryTestDaoImpl;
import fa.training.dao.impl.InterviewDaoImpl;
import fa.training.entities.Candidate;
import fa.training.entities.Interview;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InterviewDaoTest {


    static InterviewDao interviewDao;


    @BeforeAll
    public static void setUp() {
        interviewDao = new InterviewDaoImpl();
    }

    @Test
    public void testSave() {
        Interview interview = Interview.builder()
                .date(LocalDate.now())
                .interviewer("test")
                .interviewResult("test")
                .remark("test")
                .time("test")
                .comments("test")
                .build();
        Candidate candidate = Candidate.builder()
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


        assertNotNull(interviewDao.getById(interview.getId()));
        assertEquals(1, interviewDao.getAll().size());
    }

    @Test
    public void testSaveNotExist() {

        Interview interview = new Interview();

        assertThrows(Exception.class, () -> {
            interviewDao.save(interview);
        });
    }


    //    @Test
    @Test
    public void testFindAll() {

        Interview interview = Interview.builder()
                .date(LocalDate.now())
                .interviewer("test")
                .interviewResult("test")
                .remark("test")
                .time("test")
                .comments("test")
                .build();

        interviewDao.save(interview);


        assertFalse(interviewDao.getAll().isEmpty());

        assertEquals(2, interviewDao.getAll().size());
    }


    @Test
    public void testGetById() {

        Interview interview = interviewDao.getById(1);
        assertNotNull(interview);
        assertEquals("test", interview.getInterviewer());

    }

    @Test
    public void testGetByIdNotExist() {

        Interview interview = interviewDao.getById(-1);
        assertNull(interview);
    }



    @Test
    public void testUpdate() {
        Interview interview = interviewDao.getById(1);
        interview.setInterviewer("test2");
        interviewDao.update(interview);
        assertEquals("test2", interview.getInterviewer());
    }

    @Test
    public void testUpdateNotExist() {
        Interview interview = interviewDao.getById(-1);



        assertThrows(Exception.class, () -> {
            interview.setInterviewer("test2");
            interviewDao.update(interview);
        });
    }


    @Test
    public void testRemoveById() {
        interviewDao.removeById(1);
        assertTrue(interviewDao.getById(1) == null);
    }
    @Test
    public void testRemoveByIdNotExist() {
        assertThrows(Exception.class, () -> {
            interviewDao.removeById(0);
        });
    }


}
