package com.javenock.patientservice.controller;

import com.javenock.patientservice.exception.NoPatientsException;
import com.javenock.patientservice.exception.PatientNotFoundException;
import com.javenock.patientservice.model.Patient;
import com.javenock.patientservice.request.PatientRequest;
import com.javenock.patientservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //save patient
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create_patient(@RequestBody @Valid PatientRequest patientRequest){
        return patientService.createPatient(patientRequest);
    }

    @GetMapping("/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public Patient findPatientById(@PathVariable Long patientId) throws PatientNotFoundException {
        return patientService.findPatientById(patientId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> listAllPatients() throws NoPatientsException {
        return patientService.listAllPatients();
    }
}
