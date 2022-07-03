package io.marella.sunday.integration;

import io.marella.sunday.model.Visit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@RequestScoped
public class VisitRepository {

    @PersistenceContext
    private EntityManager em;


    static final Logger logger = LogManager.getLogger(VisitRepository.class.getName());

    public List<Visit> getVisits(String patientId, String visitType) {

        Query query;
        try (Session session = em.unwrap(Session.class)) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Visit> cr = cb.createQuery(Visit.class);
            Root<Visit> root = cr.from(Visit.class);
            List<Predicate> predicates = new ArrayList<>();
            if (patientId != null) {
                logger.info("Looking for patient {} ", patientId);

                Predicate patientIdPreticate = cb.equal(root.get("patientId"), patientId);
                predicates.add(patientIdPreticate);
            }
            if (visitType != null) {
                logger.info("Looking for visitType {} ", patientId);

                Predicate visitPredicate = cb.equal(root.get("visitType"), visitType);
                predicates.add(visitPredicate);
            }

            cr.select(root);
            if (!predicates.isEmpty()) {
                cr.where(predicates.toArray(new Predicate[2]));

            }
            query = session.createQuery(cr);
            return query.getResultList();
        }

    }


    public Visit getVisit(String visitId) {
        TypedQuery<Visit> q = em.createQuery("SELECT v FROM Visit v WHERE v.visitId = :visitId", Visit.class);
        q.setParameter("visitId", visitId);
        return q.getSingleResult();
    }


    public int deleteVisit(String visitId) {
        Query query = em.createQuery("DELETE FROM Visit v WHERE v.visitId = :visitId ");
        query.setParameter("visitId", visitId);
        return query.executeUpdate();

    }

    public Visit createOrUpdate(Visit visit) {
        if (visit.getVisitId() == null) {
            String visitId = UUID.randomUUID().toString();
            visit.setVisitId(visitId);
            em.persist(visit);
            return em.find(Visit.class, visitId);
        } else {
            return em.merge(visit);
        }
    }

}
