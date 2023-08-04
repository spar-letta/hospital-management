package com.javenock.inpatientservice.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class InpatientRequest {
    @NotBlank
    private String admissionDate;
    @NotBlank
    private String dischargeDate;
    @NotBlank
    private String disease;
    @Min(1)
    private double doctorCharge;
    @Min(1)
    private Long patientId;
    @NotBlank
    private String roomNumber;
}
