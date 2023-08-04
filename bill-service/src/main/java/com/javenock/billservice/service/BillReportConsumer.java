package com.javenock.billservice.service;

import com.javenock.billservice.response.BillResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BillReportConsumer {

    Logger LOGGER = LoggerFactory.getLogger(BillService.class);

    @KafkaListener(topics = "bill-report", groupId = "bill-hospital-v1")
    public void readBillReportMessage(BillResponse billResponse){
        LOGGER.info("Your Hospital Bill Report --> {}", billResponse);
        LOGGER.info("PATIENT ID : {}", billResponse.getPatientId());
    }
}
