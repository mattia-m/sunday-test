package io.marella.sunday.integration;

import io.marella.sunday.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequestScoped
public class PatientRepository {

    @PersistenceContext
    private EntityManager em;

    static final Logger logger = LogManager.getLogger(PatientRepository.class.getName());

    public List<Patient> getPatients(String surname) {

        Query query;
        try (Session session = em.unwrap(Session.class)) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Patient> cr = cb.createQuery(Patient.class);
            Root<Patient> root = cr.from(Patient.class);
            List<Predicate> predicates = new ArrayList<>();
            if (surname != null) {

                Predicate patientIdPreticate = cb.equal(root.get("surname"), surname);
                predicates.add(patientIdPreticate);
            }


            cr.select(root);

            if (!predicates.isEmpty()) {
                logger.info("Looking for patient {} ", surname);
                cr.where(predicates.toArray(new Predicate[0]));
            }
            query = session.createQuery(cr);
            return query.getResultList();
        }

    }


    public Patient getPatient(String socialSecurityId) {
        try {
            TypedQuery<Patient> q = em.createQuery("SELECT v FROM patient v WHERE v.socialSecurityId = :socialSecurityId", Patient.class);
            q.setParameter("socialSecurityId", socialSecurityId);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }


    public int deletePatient(String patientId) {
        Query query = em.createQuery("DELETE FROM patient v WHERE v.socialSecurityId = :patientId ");
        query.setParameter("socialSecurityId", patientId);
        return query.executeUpdate();

    }

    public Patient createOrUpdate(Patient patient) {

        return em.merge(patient);
    }
}


