package com.javenock.billservice.service;

import com.javenock.billservice.response.BillResponse;
import com.javenock.billservice.model.Bill;
import com.javenock.billservice.repository.BillRepository;
import com.javenock.billservice.request.DoctorChargeRequest;
import com.javenock.billservice.request.LabBillRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BillService {

    Logger LOGGER = LoggerFactory.getLogger(BillService.class);


    @Autowired
    private BillRepository billRepository;

    @Autowired
    private KafkaTemplate<String, BillResponse> template;

    public void saveLabCharges(LabBillRequest lab_bill){
        boolean existingDeails = billRepository.existsByPatientId(lab_bill.getPatientId());
        if(existingDeails == true){
            //update details from laboratory
            Bill existingBill = billRepository.findByPatientId(lab_bill.getPatientId()).get();
            existingBill.setId(existingBill.getId());
            existingBill.setLab_charge(lab_bill.getLab_charge());
            billRepository.save(existingBill);
        }else{
            //create record
            billRepository.save(Bill.builder()
                            .patientId(lab_bill.getPatientId())
                            .lab_charge(lab_bill.getLab_charge())
                    .build());
        }
    }

    public void saveDoctorCharges(DoctorChargeRequest doc_bill){
        boolean existingDeails = billRepository.existsByPatientId(doc_bill.getPatientId());
        if(existingDeails){
            //update details from laboratory
            Bill existingBill = billRepository.findByPatientId(doc_bill.getPatientId()).get();
            existingBill.setId(existingBill.getId());
            existingBill.setDoctor_charge(doc_bill.getDoctor_charge());
            existingBill.setAdmissionData(LocalDate.parse(doc_bill.getAdmissionData()));
            billRepository.save(existingBill);
        }else{
            //create record
            billRepository.save(Bill.builder()
                    .patientId(doc_bill.getPatientId())
                            .doctor_charge(doc_bill.getDoctor_charge())
                            .admissionData(LocalDate.parse(doc_bill.getAdmissionData()))
                    .build());
        }
    }

    public BillResponse printPatientBill(Long patientId){
        Bill existingBill = billRepository.findByPatientId(patientId).get();
        long days_between = DAYS.between(existingBill.getAdmissionData(), LocalDate.now());
        double days_charge = days_between * 150;
        double amount = existingBill.getDoctor_charge() + existingBill.getLab_charge() + days_charge;
        BillResponse billResponse = BillResponse.builder()
                .patientId(existingBill.getPatientId())
                .doctor_charge(existingBill.getDoctor_charge())
                .lab_charge(existingBill.getLab_charge())
                .admissionData(existingBill.getAdmissionData())
                .no_of_days(days_between)
                .no_of_days_charge(days_charge)
                .bill_amount(amount)
                .build();
        sendBillNotification(billResponse);
        return billResponse;
    }

    public void sendBillNotification(BillResponse billResponse){
        LOGGER.info(String.format("BILL REPORT IS : --> %s",billResponse.toString()));
        Message<BillResponse> billResponseMessage = MessageBuilder
                .withPayload(billResponse)
                .setHeader(KafkaHeaders.TOPIC, "bill-report")
                .build();
        template.send(billResponseMessage);
    }
}
