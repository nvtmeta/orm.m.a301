package fa.training.dao.impl;

import fa.training.dao.InterviewDao;
import fa.training.entities.Interview;
import fa.training.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class InterviewDaoImpl implements InterviewDao {
    @Override
    public void save(Interview interview) {
        Session session = HibernateUtils.getCurrentSession();

        session.beginTransaction();
        session.persist(interview);
        session.getTransaction().commit();
    }

    @Override
    public Interview getById(Integer id) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        Interview interview = session.get(Interview.class, id);
        session.getTransaction().commit();
        return interview;
    }

    @Override
    public List<Interview> getAll() {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        List<Interview> interviews = session.createQuery("FROM Interview", Interview.class).getResultList();
        session.getTransaction().commit();
        return interviews;
    }


    @Override
    public void update(Interview interview) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.merge(interview);
        session.getTransaction().commit();
    }

    @Override
    public void removeById(Integer id) {
        Interview interview = getById(id);
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.remove(interview);
        session.getTransaction().commit();
    }
}
