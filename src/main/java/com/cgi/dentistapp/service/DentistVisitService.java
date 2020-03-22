package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.repository.DentistVisitEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class DentistVisitService {

    private static final Logger logger = LoggerFactory.getLogger(DentistVisitService.class);

    @Autowired
    DentistVisitEntityRepository repo;

    @Autowired
    DentistVisitAssembler dentistVisitAssembler;

    public DentistVisitDTO addVisit(String dentistName, Date visitDateTime) {
        logger.info("Creating new visit ..");
        DentistVisitEntity visit = new DentistVisitEntity();
        visit.setDentistName(dentistName);
        visit.setVisitDateTime(visitDateTime);
        return dentistVisitAssembler.toResource(repo.save(visit));
    }

    public DentistVisitDTO updateRegistration(Long id, String dentistName, Date visitDateTime) {
        logger.info("Updating visit {}", id);
        DentistVisitEntity visit = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + id));
        visit.setDentistName(dentistName);
        visit.setVisitDateTime(visitDateTime);
        return dentistVisitAssembler.toResource(repo.save(visit));
    }

    public DentistVisitDTO getRegistrationDetails(Long id, Model model) {
        logger.info("Find registration with id {}", id);
        DentistVisitEntity visit = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid registration Id:" + id));
        DentistVisitDTO myVisit = dentistVisitAssembler.toResource(visit);
        model.addAttribute("myRegistration", myVisit);
        return myVisit;
    }

    public void getAllRegistrations(Model model) {
        logger.info("Getting all Registrations ..");
        model.addAttribute("registrations", dentistVisitAssembler.toResourceList(repo.findAll()));
        logger.info("Registration list returned!");
    }

    public void getByQuery(String query, Model model) {
        logger.info("searching for query {}", query);
        model.addAttribute("registrations", dentistVisitAssembler.toResourceList(repo.getByQuery(query)));
        logger.info("search retrieved!..");
    }


    public void deleteRegistration(Long id) {
        logger.info("Deleting registration with id {}", id);
        repo.deleteById(id);
        logger.info("Deleted!");
    }

    public Boolean dateTimeValidate(Date visitDateTime, Long id){
        return repo.doesDateTimeExists(visitDateTime, id);
    }
}
