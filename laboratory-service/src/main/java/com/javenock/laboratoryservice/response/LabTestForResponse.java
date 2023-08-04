package com.javenock.laboratoryservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabTestForResponse {
    private Long patientId;
    private String testFoDetails;
    private String send_status;
}
