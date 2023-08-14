package com.javenock.inpatientservice;

import com.javenock.inpatientservice.model.Inpatient;
import com.javenock.inpatientservice.repository.InpatientRepository;
import com.javenock.inpatientservice.request.BillDoctorChargeReq;
import com.javenock.inpatientservice.request.BillDoctorChargeRequest;
import com.javenock.inpatientservice.request.InpatientRequest;
import com.javenock.inpatientservice.response.PatientResponse;
import com.javenock.inpatientservice.response.RoomResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InpatientServiceApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private InpatientRepository inpatientRepository;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp(){
		baseUrl = baseUrl.concat(":").concat(port+"").concat("/in-patient");
	}

	@Test
	public void createInpatientRecord(){
		BillDoctorChargeReq billDoctorChargeReq = BillDoctorChargeReq.builder()
				.patientId(1L)
				.doctor_charge(Double.parseDouble("200.0"))
				.admissionData("2022-01-02")
				.build();
		InpatientRequest inpatientRequest = new InpatientRequest("2023-03-10","2023-03-17","headache",3500,1L,"123X");
		restTemplate.getForEntity("http://localhost:8086/patient/{patientId}", PatientResponse.class, inpatientRequest.getPatientId());
		restTemplate.getForEntity("http://localhost:8088/room/{roomNumber}", any(), inpatientRequest.getRoomNumber());
		restTemplate.postForEntity("http://localhost:8090/bill/doctor-charge", billDoctorChargeReq, String.class);
		ResponseEntity<Inpatient> inpatientResponseEntity = restTemplate.postForEntity(baseUrl, inpatientRequest, Inpatient.class);
		assertAll(
				() -> assertEquals(1,inpatientResponseEntity.getBody().getInpatientId()),
				() -> assertEquals("123X",inpatientRepository.findByInpatientId(1L).get().getRoomNumber())
		);
	}


}