package com.javenock.inpatientservice.service;

import com.javenock.inpatientservice.exception.NoRecordWithInpatientIdException;
import com.javenock.inpatientservice.exception.NoRecoredsFoundException;
import com.javenock.inpatientservice.model.Inpatient;
import com.javenock.inpatientservice.repository.InpatientRepository;
import com.javenock.inpatientservice.request.BillDoctorChargeRequest;
import com.javenock.inpatientservice.request.InpatientRequest;
import com.javenock.inpatientservice.response.InpatientPatientResponse;
import com.javenock.inpatientservice.response.PatientResponse;
import com.javenock.inpatientservice.response.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InpatientService {

    @Autowired
    private InpatientRepository inpatientRepository;

    @Autowired
    RestTemplate restTemplate;

    //save new record
    //@Transactional
    public Inpatient createInpatientRecord(InpatientRequest inpatientRequest) throws NoRecoredsFoundException {
        try{
            ResponseEntity<PatientResponse> entity = restTemplate.getForEntity("http://PATIENT-SERVICE/patient/{patientId}", PatientResponse.class, inpatientRequest.getPatientId());
            ResponseEntity<RoomResponse> roomDetails = restTemplate.getForEntity("http://ROOM-SERVICE/room/{roomNumber}", RoomResponse.class, inpatientRequest.getRoomNumber());
            Inpatient new_record = Inpatient.builder()
                    .admissionDate(LocalDate.parse(inpatientRequest.getAdmissionDate()))
                    .dischargeDate(LocalDate.parse(inpatientRequest.getDischargeDate()))
                    .disease(inpatientRequest.getDisease())
                    .doctorCharge(inpatientRequest.getDoctorCharge())
                    .doctorChargeUpdateBillStatus(updateBillingDoctorCharge(inpatientRequest))
                    .patientId(entity.getBody().getPatientId())
                    .roomNumber(roomDetails.getBody().getRoomNumber())
                    .build();
            return inpatientRepository.save(new_record);
        }catch (HttpClientErrorException.BadRequest ex){
            throw new NoRecoredsFoundException("..."+ ex.getMessage());
        }
    }

    public List<InpatientPatientResponse> fetchAllRecords() throws NoRecoredsFoundException {
        List<InpatientPatientResponse> inpatientRes = inpatientRepository.findAll().stream().map(this::mapInpatientRes).collect(Collectors.toList());
        return inpatientRes;
    }


    public Inpatient returnRecordById(Long inpatientId) throws NoRecordWithInpatientIdException {
        Inpatient inpatient = inpatientRepository.findByInpatientId(inpatientId).orElseThrow(() -> new NoRecordWithInpatientIdException("No Record With Inpatient Id "+inpatientId));
        return inpatient;
    }

    public List<Inpatient> retrieveAdmissionByDates(String date1, String date2) throws NoRecoredsFoundException {
        List<Inpatient> recordsByDates = inpatientRepository.queryAllByAdmissionDates(date1, date2);
        if(recordsByDates.size() > 0){
            return recordsByDates;
        }else{
            throw new NoRecoredsFoundException("No In-Patient records found.");
        }
    }

    public InpatientPatientResponse mapInpatientRes(Inpatient rec){
        ResponseEntity<PatientResponse> patient = restTemplate.getForEntity("http://PATIENT-SERVICE/patient/{patientId}", PatientResponse.class, rec.getPatientId());
        InpatientPatientResponse result = InpatientPatientResponse.builder()
                .admissionDate(rec.getAdmissionDate())
                .dischargeDate(rec.getDischargeDate())
                .disease(rec.getDisease())
                .patientId(rec.getPatientId())
                .roomNumber(rec.getRoomNumber())
                .patientResponse(patient.getBody())
                .build();
        return result;
    }

    public String updateBillingDoctorCharge(InpatientRequest inpatientRequest){
        BillDoctorChargeRequest billDoctorChargeRequest = BillDoctorChargeRequest.builder()
                .patientId(inpatientRequest.getPatientId())
                .doctor_charge(inpatientRequest.getDoctorCharge())
                .admissionData(inpatientRequest.getAdmissionDate())
                .build();
        restTemplate.postForEntity("http://BILL-SERVICE/bill/doctor-charge", billDoctorChargeRequest, String.class);
        return "charge updated successfully";
    }
}
