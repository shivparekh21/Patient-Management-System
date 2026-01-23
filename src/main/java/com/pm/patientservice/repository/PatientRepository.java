package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
