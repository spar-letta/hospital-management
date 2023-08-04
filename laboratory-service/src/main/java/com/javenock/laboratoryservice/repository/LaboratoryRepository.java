package com.javenock.laboratoryservice.repository;

import com.javenock.laboratoryservice.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    List<Laboratory> findByPatientId(Long patientId);
}
