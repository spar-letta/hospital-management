package com.javenock.billservice.controller;

import com.javenock.billservice.response.BillResponse;
import com.javenock.billservice.request.DoctorChargeRequest;
import com.javenock.billservice.request.LabBillRequest;
import com.javenock.billservice.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {

    private BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/lab-charge")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveLabCharge(@RequestBody LabBillRequest bill){
        billService.saveLabCharges(bill);
        return "saved successfully";
    }

    @PostMapping("/doctor-charge")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveDoctorCharge(@RequestBody DoctorChargeRequest bill){
        billService.saveDoctorCharges(bill);
        return "saved successfully";
    }

    @GetMapping("/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public BillResponse printBillDetails(@PathVariable Long patientId){
        return billService.printPatientBill(patientId);
    }

}
