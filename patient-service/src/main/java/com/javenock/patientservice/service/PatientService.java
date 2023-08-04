package com.javenock.patientservice.service;

import com.javenock.patientservice.exception.NoPatientsException;
import com.javenock.patientservice.exception.PatientNotFoundException;
import com.javenock.patientservice.model.Patient;
import com.javenock.patientservice.repository.PatientRepository;
import com.javenock.patientservice.request.PatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;


    public Patient createPatient(PatientRequest patientRequest) {
        Patient new_patient_record = Patient.builder()
                .name(patientRequest.getName())
                .age(patientRequest.getAge())
                .gender(patientRequest.getGender())
                .phone(patientRequest.getPhone())
                .address(patientRequest.getAddress())
                .build();
        return patientRepository.save(new_patient_record);
    }

    public Patient findPatientById(Long patientId) throws PatientNotFoundException {
        Patient patient = patientRepository.findByPatientId(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found with id :"+patientId));
        return patient;
    }


    public List<Patient> listAllPatients() throws NoPatientsException {
        List<Patient> all_patients = patientRepository.findAll();
        if(all_patients.size() < 1){
            throw new NoPatientsException("No patients registered");
        }else{
            return all_patients;
        }
    }
}
