package fa.training.dao;

import fa.training.entities.EntryTest;
import fa.training.entities.EntryTest;

import java.util.List;

public interface EntryTestDao {
    void save(EntryTest clazz);

    EntryTest getById(Integer id);

    List<EntryTest> getAll();

    void update(EntryTest student);

    void removeById(Integer id);
}
