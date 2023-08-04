package com.javenock.laboratoryservice.controller;

import com.javenock.laboratoryservice.exception.NoLabDetailsWithPatientIdException;
import com.javenock.laboratoryservice.exception.NoSuchRecordFoundException;
import com.javenock.laboratoryservice.request.LabTestResultRequest;
import com.javenock.laboratoryservice.request.LaboratoryRequest;
import com.javenock.laboratoryservice.model.Laboratory;
import com.javenock.laboratoryservice.response.LabTestForResponse;
import com.javenock.laboratoryservice.service.LaboratoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

    private LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabTestForResponse saveLabDetails(@RequestBody LaboratoryRequest laboratoryRequest){
        return laboratoryService.saveTestForRequestDetails(laboratoryRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "hospital", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "hospital")
    public CompletableFuture<String> updateLabTestResults(@PathVariable Long id, @RequestBody LabTestResultRequest labTestResultRequest) throws NoSuchRecordFoundException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return laboratoryService.updateLabTestResults(id, labTestResultRequest);
            } catch (NoSuchRecordFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return stringCompletableFuture;
    }

    public CompletableFuture<String> fallbackMethod(Long id, LabTestResultRequest labTestResultRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> "Oops something went wrong !!!");
    }

    @GetMapping("/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Laboratory> getPatientLabDetails(@PathVariable Long patientId) throws NoLabDetailsWithPatientIdException {
        return laboratoryService.getPatientLabDetails(patientId);
    }
}
