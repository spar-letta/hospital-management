package com.javenock.inpatientservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;
}
