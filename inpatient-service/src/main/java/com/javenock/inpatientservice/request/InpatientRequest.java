package com.javenock.inpatientservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
