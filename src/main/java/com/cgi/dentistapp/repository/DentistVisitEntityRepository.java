package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistVisitEntityRepository extends JpaRepository<DentistVisitEntity, Long>, CustomDentistVisitEntityRepository {
}
