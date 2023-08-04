package com.javenock.patientservice.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PatientRequest {
    @NotNull
    private String name;
    @Min(1)
    @Max(130)
    private int age;
    @NotBlank
    private String gender;
    @Pattern(regexp = "\\A[0-9]{10}\\z", message = "Use correct format")
    private String phone;
    @NotBlank
    private String address;
}
