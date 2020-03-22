package com.cgi.dentistapp.service;

import com.cgi.dentistapp.controller.DentistAppController;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DentistVisitAssembler extends ResourceAssemblerSupport<DentistVisitEntity, DentistVisitDTO> {
    public DentistVisitAssembler() {
        super(DentistAppController.class, DentistVisitDTO.class);
    }


    @Override
    public DentistVisitDTO toResource(DentistVisitEntity dentistVisitEntity) {
        if(dentistVisitEntity == null)
            return null;
        DentistVisitDTO dto = createResourceWithId(dentistVisitEntity.getId(), dentistVisitEntity);
        dto.set_id(dentistVisitEntity.getId());
        dto.setDentistName(dentistVisitEntity.getDentistName());
        dto.setVisitDateTime(dentistVisitEntity.getVisitDateTime());
        return dto;
    }



    public List<DentistVisitDTO> toResourceList(List<DentistVisitEntity> dentistVisitEntities) {
        List<DentistVisitDTO> res = new ArrayList<>();
        for (DentistVisitEntity p : dentistVisitEntities) {
            res.add(toResource(p));
        }
        return res;
    }
}
