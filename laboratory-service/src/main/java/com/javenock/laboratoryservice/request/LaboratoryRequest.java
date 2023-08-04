package com.javenock.laboratoryservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryRequest {
    @Min(1)
    private Long patientId;
    @NotBlank
    private String lab_results;
    @NotBlank
    private String testFoDetails;
    @NotBlank
    private String lab_date;
    @Min(1)
    private double amount;
}
