package fa.training.dao.impl;

import fa.training.dao.CandidateDao;
import fa.training.entities.Candidate;
import fa.training.entities.EntryTest;
import fa.training.entities.Interview;
import fa.training.utils.HibernateUtils;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.time.LocalDate;
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
    // Additional methods for the requested operations

    @Override
    public List<Candidate> findBySkillAndSkillLevel(String skill, int skillLevel) {
        try (Session session = HibernateUtils.openSession()) {
            TypedQuery<Candidate> query = session.createQuery("FROM Candidate WHERE skill = :skill AND level = :level", Candidate.class);
            query.setParameter("skill", skill);
            query.setParameter("level", skillLevel);
            return query.getResultList();
        }
    }

    @Override
    public List<Candidate> findByForeignLanguageAndSkill(String foreignLanguage, String skill) {
        try (Session session = HibernateUtils.openSession()) {
            TypedQuery<Candidate> query = session.createQuery("FROM Candidate WHERE foreignLanguage = :foreignLanguage AND skill = :skill", Candidate.class);
            query.setParameter("foreignLanguage", foreignLanguage);
            query.setParameter("skill", skill);
            return query.getResultList();
        }
    }

    @Override
    public List<Candidate> findBySkillAndEntryTestResult(String skill, LocalDate entryTestDate) {
        try (Session session = HibernateUtils.getCurrentSession()) {
            TypedQuery<Candidate> query = session.createQuery("FROM Candidate WHERE skill = :skill AND entryTest.result = :entryTestDate", Candidate.class);
            query.setParameter("skill", skill);
            query.setParameter("entryTestDate", entryTestDate);
            return query.getResultList();
        }
    }

    @Override
    public List<Candidate> findByInterviewDate(LocalDate interviewDate) {
        try (Session session = HibernateUtils.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(Candidate.class);
            Root<Candidate> root = criteriaQuery.from(Candidate.class);
            Join<Candidate, Interview> interviewJoin = root.join("interviews");
            criteriaQuery.select(root);
            criteriaQuery.where(builder.equal(interviewJoin.get("interviewDate"), interviewDate));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void updateRemark() {
        try (Session session = HibernateUtils.getCurrentSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Candidate> criteriaUpdate = builder.createCriteriaUpdate(Candidate.class);
            Root<Candidate> root = criteriaUpdate.from(Candidate.class);
            criteriaUpdate.set(root.get("remark"), "inactive");
            Predicate contactInfoPredicate = builder.or(
                    builder.isNull(root.get("phone")),
                    builder.isNull(root.get("email")),
                    builder.isNull(root.get("cv"))
            );
            criteriaUpdate.where(contactInfoPredicate);
            session.createQuery(criteriaUpdate).executeUpdate();
        }
    }

//    @Override
//    public List<Candidate> getPagedCandidates(int pageNumber, int pageSize) {
//        try (Session session = HibernateUtils.getCurrentSession()) {
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(Candidate.class);
//            Root<Candidate> root = criteriaQuery.from(Candidate.class);
//            criteriaQuery.select(root);
//            CriteriaQuery<Candidate> pagedCriteriaQuery = criteriaQuery
//                    .orderBy(builder.asc(root.get("id")))
//                    .getOrderList((pageNumber - 1) * pageSize)
//                    .setMaxResults(pageSize);
//            return session.createQuery(pagedCriteriaQuery).getResultList();
//        }
//    }
}
