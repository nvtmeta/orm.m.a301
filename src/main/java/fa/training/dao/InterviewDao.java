package fa.training.dao;

import fa.training.entities.Interview;

import java.util.List;

public interface InterviewDao {

    void save(Interview clazz);

    Interview getById(Integer id);

    List<Interview> getAll();

    void update(Interview student);

    void removeById(Integer id);
}
