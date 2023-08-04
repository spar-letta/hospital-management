package com.javenock.inpatientservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "INPATIENT_TB")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inpatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inpatientId;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String disease;
    private double doctorCharge;
    private String doctorChargeUpdateBillStatus;
    private Long patientId;
    private String roomNumber;

}
