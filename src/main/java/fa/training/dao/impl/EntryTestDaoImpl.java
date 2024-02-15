package fa.training.dao.impl;

import fa.training.dao.EntryTestDao;
import fa.training.entities.EntryTest;
import fa.training.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class EntryTestDaoImpl  implements EntryTestDao {

    @Override
    public void save(EntryTest entryTest) {
        Session session = HibernateUtils.getCurrentSession();

        session.beginTransaction();
        session.persist(entryTest);
        session.getTransaction().commit();
    }

    @Override
    public EntryTest getById(Integer id) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        EntryTest entryTest = session.get(EntryTest.class, id);
        session.getTransaction().commit();
        return entryTest;
    }

    @Override
    public List<EntryTest> getAll() {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        List<EntryTest> entryTests = session.createQuery("FROM EntryTest", EntryTest.class).getResultList();
        session.getTransaction().commit();
        return entryTests;
    }


    @Override
    public void update(EntryTest entryTest) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.merge(entryTest);
        session.getTransaction().commit();
    }

    @Override
    public void removeById(Integer id) {
        EntryTest entryTest = getById(id);
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.remove(entryTest);
        session.getTransaction().commit();
    }
    
}
