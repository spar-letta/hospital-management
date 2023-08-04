package com.javenock.laboratoryservice.service;

import com.javenock.laboratoryservice.exception.NoSuchRecordFoundException;
import com.javenock.laboratoryservice.request.LabChargeRequest;
import com.javenock.laboratoryservice.request.LabTestResultRequest;
import com.javenock.laboratoryservice.request.LaboratoryRequest;
import com.javenock.laboratoryservice.exception.NoLabDetailsWithPatientIdException;
import com.javenock.laboratoryservice.model.Laboratory;
import com.javenock.laboratoryservice.repository.LaboratoryRepository;
import com.javenock.laboratoryservice.response.LabTestForResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.summingDouble;

@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    // by doctor
    public LabTestForResponse saveTestForRequestDetails(LaboratoryRequest laboratoryRequest) {
        Laboratory laboratory = Laboratory.builder()
                .patientId(laboratoryRequest.getPatientId())
                .testFoDetails(laboratoryRequest.getTestFoDetails())
                .build();
        Laboratory savedLaboratory = laboratoryRepository.save(laboratory);
        LabTestForResponse labTestForResponse = LabTestForResponse.builder()
                .patientId(savedLaboratory.getPatientId())
                .testFoDetails(savedLaboratory.getTestFoDetails())
                .send_status("Lab test send successfully")
                .build();
        return labTestForResponse;
    }


    public List<Laboratory> getPatientLabDetails(Long patientId) throws NoLabDetailsWithPatientIdException {
        List<Laboratory> labDetails = laboratoryRepository.findByPatientId(patientId);
        if(labDetails.size() > 0){
            return labDetails;
        }else{
            throw new NoLabDetailsWithPatientIdException("No laboratory details found for patient with id "+patientId);
        }
    }

    //by lab technician
    @Transactional
    public String updateLabTestResults(Long id, LabTestResultRequest labTestResultRequest) throws NoSuchRecordFoundException {
        Laboratory laboratory = laboratoryRepository.findById(id).orElseThrow(() -> new NoSuchRecordFoundException("No such record found"));

        laboratory.setId(id);
        laboratory.setLab_results(labTestResultRequest.getLab_results());
        laboratory.setLab_date(LocalDate.now());
        laboratory.setTest_time(LocalTime.now());
        laboratory.setAmount(labTestResultRequest.getAmount());
        laboratoryRepository.save(laboratory);

        LabChargeRequest req = LabChargeRequest.builder()
                .patientId(laboratory.getPatientId())
                .lab_charge(calculatePatientLabCharges(laboratory.getPatientId()))
                .build();
        restTemplate.postForEntity("http://BILL-SERVICE/bill/lab-charge", req, String.class);
        return "saved successfully";
    }

    public double calculatePatientLabCharges(Long patientId){
        List<Laboratory> labDetails = laboratoryRepository.findByPatientId(patientId);
        double TotalLabCharge = labDetails.stream().collect(summingDouble(f -> f.getAmount()));
        return TotalLabCharge;
    }
}
