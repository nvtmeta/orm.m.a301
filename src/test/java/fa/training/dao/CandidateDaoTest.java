package fa.training.dao;

import fa.training.dao.impl.CandidateDaoImpl;
import fa.training.entities.Candidate;
import fa.training.entities.EntryTest;
import fa.training.entities.Interview;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .fullName("Bill")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("3234234")
                .email("Bill@gmail.com")
                .skill("dotnet")
                .foreignLanguage("china")
                .level(3)
                .cv("bill.cv")
                .allocationStatus(0)
                .remark("normal")
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

        Candidate candidate = new Candidate();

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

//    abnormal case


    @Test
    public void testFullNameNotNull() {
//        create new candidate
        Candidate candidate = Candidate.builder()
                .fullName("Jack")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("0112255")
                .email("jack@gmail.com")
                .skill("java")
                .foreignLanguage("english")
                .level(3)
                .cv("jack.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        candidateDao.save(candidate);


        List<Candidate> candidates = candidateDao.getAll();
        candidates.forEach(c -> {
            assertNotNull(c.getFullName());
        });

    }

    @Test
    public void testDateOfBirthNotNull() {
        List<Candidate> candidates = candidateDao.getAll();
        candidates.forEach(c -> {
            assertNotNull(c.getDateOfBirth());
        });
    }

    @Test
    public void testGraduationYearNotNull() {
        List<Candidate> candidates = candidateDao.getAll();
        candidates.forEach(c -> {
            assertNotNull(c.getGraduationYear());
        });

    }

    @Test
    public void testPhoneNotNull() {
        List<Candidate> candidates = candidateDao.getAll();
        candidates.forEach(c -> {
            assertNotNull(c.getPhone());
        });
    }

    @Test
    public void testEmailNotNull() {
        List<Candidate> candidates = candidateDao.getAll();
        candidates.forEach(c -> {
            assertNotNull(c.getEmail());
        });
    }


    @Test
    public void testPhoneUnique() {

        List<Candidate> candidateList = candidateDao.getAll();
        Set<String> existingPhones = new HashSet<>();
        candidateList.forEach(candidate -> {
            assertFalse(existingPhones.contains(candidate.getPhone()));
            existingPhones.add(candidate.getPhone());
        });
    }

    @Test
    public void testPhoneDuplicate() {
//        find candidate existed
        Candidate candidate = candidateDao.getAll().getFirst();
        Candidate candidate2 = Candidate.builder()
                .fullName("test")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone(candidate.getPhone())
                .email("test@gmail.com")
                .skill("java")
                .foreignLanguage("english")
                .level(3)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();
        assertThrows(Exception.class, () -> {
            candidateDao.save(candidate2);
        });

    }

    @Test
    public void testEmailUnique() {
        List<Candidate> candidateList = candidateDao.getAll();

        Set<String> existingEmails = new HashSet<>();

        candidateList.forEach(candidate -> {
            assertFalse(existingEmails.contains(candidate.getEmail()));
            existingEmails.add(candidate.getEmail());
        });
    }

    @Test
    public void testEmailDuplicate() {
        Candidate candidate = candidateDao.getAll().getFirst();
        Candidate candidate2 = Candidate.builder()
                .fullName("test")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("0112255")
                .email(candidate.getEmail())
                .skill("java")
                .foreignLanguage("english")
                .level(3)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();
        assertThrows(Exception.class, () -> {
            candidateDao.save(candidate2);
        });
    }

    @Test
    public void testGenderValidValues() {
        List<Candidate> candidateList = candidateDao.getAll();
        candidateList.forEach(candidate -> {
            assertTrue(candidate.getGender() == 0 || candidate.getGender() == 1);
        });
    }

    @Test
    public void testGenderInvalidValues() {
        Candidate candidate = Candidate.builder()
                .fullName("test")
                .dateOfBirth(LocalDate.now())
                .graduationYear(LocalDate.now())
                .phone("011225532234234")
                .email("CharliePuth@gmail.com")
                .skill("java")
                .foreignLanguage("english")
                .level(3)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .gender(4)
                .build();


        assertThrows(Exception.class, () -> {
            candidateDao.save(candidate);
        });
    }

    @Test
    public void testLevelValidRange() {
        List<Candidate> candidateList = candidateDao.getAll();
        candidateList.forEach(candidate -> {
            assertTrue(candidate.getLevel() >= 1 && candidate.getLevel() <= 7);
        });
    }

    @Test
    public void testLevelInvalidRange() {
        Candidate candidate = Candidate.builder()
                .fullName("test")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("2343221")
                .email("Puth@gmail.com")
                .skill("java")
                .foreignLanguage("english")
                .level(-9)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        assertThrows(Exception.class, () -> {
            candidateDao.save(candidate);
        });
    }

    //    a) Find all of the candidate that has skill is 'Angluar’ and skill level is 2.
    @Test
    public void testFindSkillAndSkillLevel() {
        Candidate candidateNew = Candidate.builder()
                .fullName("Rose")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("011225323532")
                .email("Rose@gmail.com")
                .skill("Angluar")
                .foreignLanguage("english")
                .level(2)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        candidateDao.save(candidateNew);

        List<Candidate> candidateList = candidateDao.findBySkillAndSkillLevel("Angluar", 2);
        System.out.println("candidateList = " + candidateList);
        candidateList.forEach(candidate -> {
            assertEquals("Angluar", candidate.getSkill());
            assertEquals(2, candidate.getLevel());
        });
    }

    //    b) Find all of the candidate that has foreign language is 'Japanese' and skill is 'Python/ML'.
    @Test
    public void testFindSkillAndForeignLanguage() {
        Candidate candidateNew = Candidate.builder()
                .fullName("Warrenton")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("1111")
                .email("Warrenton@gmail.com")
                .skill("Python/ML")
                .foreignLanguage("Japanese")
                .level(2)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        List<Candidate> candidateList = candidateDao.findByForeignLanguageAndSkill("Japanese", "Python/ML");
        candidateList.forEach(candidate -> {
            assertEquals("Python/ML", candidate.getSkill());
            assertEquals("Japanese", candidate.getForeignLanguage());
        });
    }

    //c) Find all of the candidate by skill and entry test result (that has skill is ‘Java’ and pass entry test
//on '1-Oct-2020').
    @Test
    public void testFindSkillAndEntryTest() {

        Candidate candidateNew = Candidate.builder()
                .fullName("Elon")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("2222")
                .email("Elon@gmail.com")
                .skill("Java")
                .foreignLanguage("Japanese")
                .level(2)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        EntryTest entryTestNew = EntryTest.builder()
                .time("test")
                .date(LocalDate.of(2020, 10, 1))
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
        entryTestNew.setCandidate(candidateNew);
        entryTests.add(entryTestNew);

        candidateNew.setEntryTests(entryTests);

        candidateDao.save(candidateNew);

        LocalDate entryTestDate = LocalDate.of(2020, 10, 1);

        List<Candidate> candidateList = candidateDao.findBySkillAndEntryTestResult("Java", entryTestDate);
        System.out.println("candidateList" + candidateList);

        candidateList.forEach(candidate -> {
            assertEquals("Java", candidate.getSkill());
            candidate.getEntryTests().forEach(entryTest -> {
                assertEquals("pass", entryTest.getResult()); // Assert that all entry test results are "pass"
                assertEquals(entryTestDate, entryTest.getDate()); // Assert the entry test date
            });
        });

    }


    //d) Find all the candidate that pass interview on '15-Oct-2020'.
    @Test
    public void testFindPassInterview() {

        LocalDate interviewDate = LocalDate.of(2020, 10, 15);

        Candidate candidateNew = Candidate.builder()
                .fullName("Tim")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("3333")
                .email("Tim@gmail.com")
                .skill("Python/ML")
                .foreignLanguage("Japanese")
                .level(2)
                .cv("test.cv")
                .allocationStatus(0)
                .remark("good")
                .build();

        Interview interviewNew = Interview.builder()
                .time("test")
                .date(interviewDate)
                .interviewer("test")
                .comments("test")
                .interviewResult("pass")
                .remark("active")
                .build();


        Set<Interview> interviews = new HashSet<>();
        interviewNew.setCandidate(candidateNew);
        interviews.add(interviewNew);
        candidateNew.setInterviews(interviews);

        candidateDao.save(candidateNew);

        List<Candidate> candidateList = candidateDao.findByInterviewDate(interviewDate);
        System.out.println("candidateList" + candidateList);
        candidateList.forEach(candidate -> {
            System.out.println("candidate.getInterviews() = " + candidate.getInterviews());
            candidate.getInterviews().forEach(interview -> {
                assertEquals(interviewDate, interview.getDate());
                assertEquals("pass", interview.getInterviewResult());
            });
        });
    }

    //e) Update remark is inactive for candidates who do not have either phone, email and cv.
    @Test
    @Transactional
    public void testUpdateRemarkInactiveForCandidatesWithoutContactInfo() {

        Candidate candidateNew = Candidate.builder()
                .fullName("Tim cook")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("")
                .email("")
                .skill("Python/ML")
                .foreignLanguage("Japanese")
                .level(2)
                .cv("")
                .allocationStatus(0)
                .remark("active")
                .build();


        candidateDao.save(candidateNew);

        candidateDao.updateRemarkInactiveForCandidatesWithoutContactInfo();

        Candidate candidate = candidateDao.getById(candidateNew.getId());
        System.out.println("candidate= " + candidate);
        assertEquals("inactive", candidate.getRemark());

    }

    //f) Create a method to proceed paging operation for Candidate use Hibernate Criteria Query
//Language.
    @Test
    public void testGetCandidatesByPage() {

        List<Candidate> candidateList = candidateDao.getCandidatesByPage(1, 2);
        System.out.println("candidateList = " + candidateList);

        assertEquals(2, candidateList.size());

        List<Candidate> candidateList2 = candidateDao.getCandidatesByPage(2, 1);
        System.out.println("candidateList2 = " + candidateList2);
        assertEquals(1, candidateList2.size());

        List<Candidate> candidateList3 = candidateDao.getCandidatesByPage(3, 10);
        System.out.println("candidateList3 = " + candidateList3);
        assertEquals(0, candidateList3.size());


    }
}
