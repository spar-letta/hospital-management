package com.javenock.billservice.repository;

import com.javenock.billservice.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByPatientId(Long patientId);
    boolean existsByPatientId(Long patientId);
}
