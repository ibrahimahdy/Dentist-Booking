package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/register")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO) {
        return "register";
    }

    @PostMapping("/register")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if(dentistVisitService.dateTimeValidate(dentistVisitDTO.getVisitDateTime(), 0L)){
            bindingResult.rejectValue("visitDateTime", "date.error.used", "Choose different time!");
            return "register";
        }

        DentistVisitDTO visit = dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), dentistVisitDTO.getVisitDateTime());
        return "redirect:/results?regId="+ visit.get_id();
    }

    @GetMapping("/editRegistration/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        DentistVisitDTO visit = dentistVisitService.getRegistrationDetails(id, model);
        if(visit !=null){
            return "edit";
        }
        return "redirect:/error";
    }


    @PostMapping("/editRegistration/{id}")
    public String postEditForm(@PathVariable Long id, @Valid @ModelAttribute("myRegistration") DentistVisitDTO myRegistration, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            myRegistration.set_id(id);
            return "edit";
        }

        if(dentistVisitService.dateTimeValidate(myRegistration.getVisitDateTime(), id)){
            bindingResult.rejectValue("visitDateTime", "date.error.used", "Choose different time!");
            myRegistration.set_id(id);
            return "edit";
        }

        DentistVisitDTO visit = dentistVisitService.updateRegistration(id, myRegistration.getDentistName(), myRegistration.getVisitDateTime());
        if(visit !=null){
            return "redirect:/results?regId="+ visit.get_id();
        }
        return "error";
    }

    @GetMapping("/registration/{id}")
    public String showRegistrationDetails(@PathVariable("id") Long id, Model model) {
        DentistVisitDTO visit = dentistVisitService.getRegistrationDetails(id, model);
        if(visit !=null){
            return "details" ;
        }
        return "error";
    }

    @GetMapping("/registrations")
    public String showAllRegistrations(Model model) {
        dentistVisitService.getAllRegistrations(model);
        return "registrations";
    }

    @GetMapping("/search")
    public String showSearchResult(@RequestParam(name = "query", required = false) String query, Model model) {
        dentistVisitService.getByQuery(query, model);
        return "registrations" ;
    }

    @PostMapping("/deleteRegistration/{id}")
    public String deleteRegistration(@PathVariable Long id){
        dentistVisitService.deleteRegistration(id);
        return "redirect:/registrations";
    }
}
