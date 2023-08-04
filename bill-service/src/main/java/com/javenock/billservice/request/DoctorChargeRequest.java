package com.javenock.billservice.request;

import lombok.Data;

@Data
public class DoctorChargeRequest {
    private Long patientId;
    private double doctor_charge;
    private String admissionData;
}
