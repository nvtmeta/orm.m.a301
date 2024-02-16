package fa.training.dao;

import fa.training.dao.impl.CandidateDaoImpl;
import fa.training.entities.Candidate;
import fa.training.entities.EntryTest;
import fa.training.entities.Interview;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CandidateDaoTest {


    static CandidateDao candidateDao;


    @BeforeAll
    public static void setUp() {
        candidateDao = new CandidateDaoImpl();
    }


    @Test
    public void testSave() {
        Candidate candidate = Candidate.builder()
                .fullName("test")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("test")
                .email("test")
                .skill("2")
                .foreignLanguage("test")
                .level(0)
                .cv("test")
                .allocationStatus(0)
                .remark("test")
                .build();


        EntryTest entryTest = EntryTest.builder()
                .time("test")
                .date(LocalDate.now())
                .languageValuator("test")
                .languageResult("0")
                .technicalValuator("test")
                .technicalResult("1")
                .result("pass")
                .remark("test")
                .entryTestSkill("test")
                .build();
        Set<EntryTest> entryTests =
                new HashSet<>();
        entryTest.setCandidate(candidate);
        entryTests.add(entryTest);


        Interview interview = Interview.builder()
                .time("test")
                .date(LocalDate.now())
                .interviewer("test")
                .comments("test")
                .interviewResult("test")
                .remark("test")
                .build();
        interview.setCandidate(candidate);
        Set<Interview> interviewSet = new HashSet<>();
        interviewSet.add(interview);


        candidate.setEntryTests(entryTests);
        candidate.setInterviews(interviewSet);

        candidateDao.save(candidate);

        assertNotNull(candidate.getId());

        assertEquals(1, candidateDao.getAll().size());

    }

    @Test
    public void testSaveNotExist() {

        Candidate candidate = Candidate.builder().build();

        assertThrows(Exception.class, () -> {
            candidateDao.save(candidate);
        });
    }

    @Test
    public void testGetAll() {
        List<Candidate> candidates = candidateDao.getAll();
        assertEquals(1, candidates.size());
    }

    @Test
    public void testGetById() {
        Candidate candidate = candidateDao.getById(10);
        assertNotNull(candidate);
    }

    @Test
    public void testGetByIdNotExist() {
        Candidate candidate = candidateDao.getById(-1);
        assertNull(candidate);
    }

    @Test
    public void testUpdate() {
        Candidate candidate = candidateDao.getById(10);
        candidate.setFullName("test update");
        candidateDao.update(candidate);
        assertEquals("test update", candidate.getFullName());
    }

    @Test
    public void testUpdateNotExist() {
        Candidate candidate = candidateDao.getById(-1);
        assertThrows(Exception.class, () -> {
            candidateDao.update(candidate);
        });
    }

    @Test
    public void testRemoveById() {
        Candidate candidate = candidateDao.getById(10);
        candidateDao.removeById(candidate.getId());
        assertNull(candidateDao.getById(candidate.getId()));
    }

    @Test
    public void testRemoveByIdNotExist() {
        Candidate candidate = candidateDao.getById(-1);
        assertThrows(Exception.class, () -> {
            candidateDao.removeById(candidate.getId());
        });
    }


}
