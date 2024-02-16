package fa.training.dao;

import fa.training.entities.Candidate;

import java.time.LocalDate;
import java.util.List;

public interface CandidateDao {
    void save(Candidate clazz);

    Candidate getById(Integer id);

    List<Candidate> getAll();

    void update(Candidate student);

    void removeById(Integer id);


    List<Candidate> findBySkillAndSkillLevel(String skill, int skillLevel);

    List<Candidate> findByForeignLanguageAndSkill(String foreignLanguage, String skill);

    List<Candidate> findBySkillAndEntryTestResult(String skill, LocalDate entryTestDate);

    List<Candidate> findByInterviewDate(LocalDate interviewDate);

    void updateRemark();
}
