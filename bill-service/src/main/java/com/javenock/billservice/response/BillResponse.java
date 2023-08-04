package com.javenock.billservice.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private Long patientId;
    private double doctor_charge;
    private double lab_charge;
    private LocalDate admissionData;
    private long no_of_days;
    private double no_of_days_charge;
    private double bill_amount;
}

