package fa.training.dao;

import fa.training.entities.Candidate;

import java.util.List;

public interface CandidateDao {
    void save(Candidate clazz);

    Candidate getById(Integer id);

    List<Candidate> getAll();

    void update(Candidate student);

    void removeById(Integer id);
}
