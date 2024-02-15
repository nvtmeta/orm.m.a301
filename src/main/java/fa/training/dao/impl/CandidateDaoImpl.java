package fa.training.dao.impl;

import fa.training.dao.CandidateDao;
import fa.training.entities.Candidate;
import fa.training.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class CandidateDaoImpl implements CandidateDao {
    @Override
    public void save(Candidate candidate) {
        Session session = HibernateUtils.getCurrentSession();

        session.beginTransaction();
        session.persist(candidate);
        session.getTransaction().commit();
    }

    @Override
    public Candidate getById(Integer id) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        Candidate candidate = session.get(Candidate.class, id);
        session.getTransaction().commit();
        return candidate;
    }

    @Override
    public List<Candidate> getAll() {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        List<Candidate> candidates = session.createQuery("FROM Candidate", Candidate.class).getResultList();
        session.getTransaction().commit();
        return candidates;
    }


    @Override
    public void update(Candidate candidate) {
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.merge(candidate);
        session.getTransaction().commit();
    }

    @Override
    public void removeById(Integer id) {
        Candidate candidate = getById(id);
        Session session = HibernateUtils.getCurrentSession();
        session.beginTransaction();
        session.remove(candidate);
        session.getTransaction().commit();
    }
}
