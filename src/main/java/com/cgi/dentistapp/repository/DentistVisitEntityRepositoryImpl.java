package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class DentistVisitEntityRepositoryImpl implements CustomDentistVisitEntityRepository {

    @Autowired
    EntityManager em;

    @Override
    public List<DentistVisitEntity> getByQuery(String query) {
        return em.createQuery("SELECT p FROM DentistVisitEntity p WHERE LOWER(p.id) like LOWER(concat('%', ?1, '%')) or LOWER(p.dentistName) like LOWER(concat('%', ?1, '%')) or LOWER(p.visitDateTime) like LOWER(concat('%', ?1, '%'))",
                DentistVisitEntity.class)
                .setParameter(1, query)
                .getResultList();
    }


    @Override
    public Boolean doesDateTimeExists(Date visitDateTime, Long id) {
        return em.createQuery("SELECT (CASE WHEN (COUNT (p) > 0) THEN TRUE ELSE FALSE END) FROM DentistVisitEntity p WHERE LOWER(p.visitDateTime) = ?1 AND (p.id <> ?2)",
                Boolean.class)
                .setParameter(1, visitDateTime)
                .setParameter(2, id)
                .getSingleResult();
    }
}
