package com.javenock.inpatientservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDoctorChargeRequest {
    private Long patientId;
    private double doctor_charge;
    private String admissionData;
}
