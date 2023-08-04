package com.javenock.billservice.request;

import lombok.Data;

@Data
public class LabBillRequest {
    private Long patientId;
    private double lab_charge;
}
