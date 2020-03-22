package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistVisitEntity;

import java.util.Date;
import java.util.List;

public interface CustomDentistVisitEntityRepository {
    List<DentistVisitEntity> getByQuery(String query);
    Boolean doesDateTimeExists(Date visitDateTime, Long id);
}



