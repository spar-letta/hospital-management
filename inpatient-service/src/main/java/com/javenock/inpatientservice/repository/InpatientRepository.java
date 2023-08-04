package com.javenock.inpatientservice.repository;

import com.javenock.inpatientservice.model.Inpatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InpatientRepository extends JpaRepository<Inpatient, Long> {
    Optional<Inpatient> findByInpatientId(Long inpatientId);

    @Query(value = "SELECT * FROM INPATIENT_TB WHERE (ADMISSION_DATE >= ?1 AND ADMISSION_DATE <= ?2)", nativeQuery = true)
    List<Inpatient> queryAllByAdmissionDates(String date1, String date2);
}
