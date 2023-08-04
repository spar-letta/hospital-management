package com.javenock.inpatientservice.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InpatientPatientResponse {
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String disease;
    private Long patientId;
    private PatientResponse patientResponse;
    private String roomNumber;
}
