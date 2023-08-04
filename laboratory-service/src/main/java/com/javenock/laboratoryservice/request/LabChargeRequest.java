package com.javenock.laboratoryservice.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabChargeRequest {
    private Long patientId;
    private double lab_charge;
}
