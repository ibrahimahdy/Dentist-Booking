package com.cgi.dentistapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class DentistVisitDTO extends ResourceSupport {

    Long _id;

    @Size(min = 1, max = 50)
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd/mm/yyyy h:00 a")
    Date visitDateTime;
}

