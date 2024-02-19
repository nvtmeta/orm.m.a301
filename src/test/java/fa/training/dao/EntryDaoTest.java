package fa.training.dao;

import fa.training.dao.impl.CandidateDaoImpl;
import fa.training.dao.impl.EntryTestDaoImpl;
import fa.training.entities.Candidate;
import fa.training.entities.EntryTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntryDaoTest {


    static EntryTestDao entryTestDao;

    @BeforeAll
    public static void setUp() {
        entryTestDao = new EntryTestDaoImpl();
    }


    @Test
    public void testSave() {

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


        Candidate candidate = Candidate.builder()
                .fullName("Gate")
                .dateOfBirth(LocalDate.now())
                .gender(0)
                .graduationYear(LocalDate.now())
                .phone("998989")
                .email("Gate@gmail.com")
                .skill("dotnet")
                .foreignLanguage("china")
                .level(3)
                .cv("Gate.cv")
                .allocationStatus(0)
                .remark("normal")
                .build();

        entryTest.setCandidate(candidate);

        entryTestDao.save(entryTest);

        assertNotNull(entryTest.getId());

        EntryTest entryTest2 = entryTestDao.getById(entryTest.getId());
        assertEquals(entryTest.getId(), entryTest2.getId());
    }


    @Test
    public void testSaveNotExist() {

        EntryTest entryTest = new EntryTest();

        assertThrows(Exception.class, () -> {
            entryTestDao.save(entryTest);
        });
    }

    @Test
    public void testGetAll() {
        List<EntryTest> entryTests = entryTestDao.getAll();
        assertEquals(3, entryTests.size());
    }

    @Test
    public void testGetById() {
        EntryTest entryTest = entryTestDao.getById(15);
        assertNotNull(entryTest);
    }

    @Test
    public void testGetByIdNotExist() {
        EntryTest entryTest = entryTestDao.getById(-1);
        assertNull(entryTest);
    }

    @Test
    public void testUpdate() {
        EntryTest entryTest = entryTestDao.getById(15);
        entryTest.setResult("fail");
        entryTestDao.update(entryTest);
        assertEquals("fail", entryTest.getResult());
    }

    @Test
    public void testUpdateNotExist() {
        EntryTest entryTest = new EntryTest();
        assertThrows(Exception.class, () -> {
            entryTestDao.update(entryTest);
        });
    }

    @Test
    public void testRemoveById() {
        EntryTest entryTest = entryTestDao.getById(15);
        entryTestDao.removeById(entryTest.getId());
        assertNull(entryTestDao.getById(entryTest.getId()));
    }

    @Test
    public void testRemoveByIdNotExist() {
        EntryTest entryTest = entryTestDao.getById(-1);
        assertThrows(Exception.class, () -> {
            entryTestDao.removeById(entryTest.getId());
        });
    }

    //o languageResult, techinicalResult: accepts value range from 0 to 10 only.
    @Test
    public void testLanguageResultInValidRange() {
        EntryTest entryTest = EntryTest.builder()
                .time("test")
                .date(LocalDate.now())
                .languageValuator("test")
                .technicalValuator("test")
                .technicalResult("1")
                .result("pass")
                .remark("test")
                .entryTestSkill("test")
                .build();

        entryTest.setLanguageResult("15"); // Set an invalid value outside the range

        assertThrows(Exception.class, () -> {
            // Save the EntryTest object, which should trigger the validation
        });
    }

    @Test
    public void testTechnicalResultInValidRange() {
        EntryTest entryTest = EntryTest.builder()
                .time("test")
                .date(LocalDate.now())
                .languageValuator("test")
                .languageResult("0")
                .technicalValuator("test")
                .result("pass")
                .remark("test")
                .entryTestSkill("test")
                .build();

        entryTest.setTechnicalResult("-5"); // Set an invalid value outside the range

        assertThrows(Exception.class, () -> {
            // Save the EntryTest object, which should trigger the validation
            entryTestDao.save(entryTest);
        });
    }


//o result: 'pass' or 'fail' value.

    @Test
    public void testResultInValidValues() {
        EntryTest entryTest = EntryTest.builder()
                .time("test")
                .date(LocalDate.now())
                .languageValuator("test")
                .languageResult("0")
                .technicalValuator("test")
                .technicalResult("1")
                .remark("test")
                .entryTestSkill("test")
                .build();

        entryTest.setResult("invalid"); // Set an invalid value

        assertThrows(Exception.class, () -> {
            // Save the EntryTest object, which should trigger the validation
            entryTestDao.save(entryTest);
        });
    }
}
