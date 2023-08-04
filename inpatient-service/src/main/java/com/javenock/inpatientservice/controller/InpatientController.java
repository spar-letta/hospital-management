package com.javenock.inpatientservice.controller;

import com.javenock.inpatientservice.exception.NoRecordWithInpatientIdException;
import com.javenock.inpatientservice.exception.NoRecoredsFoundException;
import com.javenock.inpatientservice.model.Inpatient;
import com.javenock.inpatientservice.request.InpatientRequest;
import com.javenock.inpatientservice.response.InpatientPatientResponse;
import com.javenock.inpatientservice.service.InpatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/in-patient")
public class InpatientController {

    private InpatientService inpatientService;

    public InpatientController(InpatientService inpatientService) {
        this.inpatientService = inpatientService;
    }

    // create record for a patient
    @PostMapping
    public ResponseEntity<Inpatient> create_in_patient_record(@RequestBody @Valid InpatientRequest inpatientRequest) throws NoRecoredsFoundException {
        return new ResponseEntity<>(inpatientService.createInpatientRecord(inpatientRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public List<InpatientPatientResponse> list_all_records() throws NoRecoredsFoundException {
        return new ResponseEntity<>(inpatientService.fetchAllRecords(), HttpStatus.OK).getBody();
    }

    @GetMapping("/{inpatientId}")
    public Inpatient fetch_record_by_id(@PathVariable Long inpatientId) throws NoRecordWithInpatientIdException {
        return new ResponseEntity<>(inpatientService.returnRecordById(inpatientId), HttpStatus.OK).getBody();
    }

    @GetMapping("/query-by-dates")
    public List<Inpatient> list_all_admitted_range_of_dates(@RequestParam("date1") String date1, @RequestParam("date2") String date2) throws NoRecoredsFoundException {
        return new ResponseEntity<>(inpatientService.retrieveAdmissionByDates(date1,date2), HttpStatus.OK).getBody();
    }

}
