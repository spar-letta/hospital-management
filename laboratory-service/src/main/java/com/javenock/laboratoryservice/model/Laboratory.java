package com.javenock.laboratoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LABORATORY_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private String testFoDetails;   // filled by doctor
    private String lab_results;  // filled by lab tech
    private LocalDate lab_date;  // filled by lab tech
    private LocalTime test_time;
    private double amount;  // filled by lab tech
}
