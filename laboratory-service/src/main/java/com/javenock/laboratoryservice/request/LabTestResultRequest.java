package com.javenock.laboratoryservice.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class LabTestResultRequest {
    @NotBlank
    private String lab_results;
    @Min(1)
    private double amount;
}
