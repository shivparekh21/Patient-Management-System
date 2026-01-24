package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);

    // check for the email against id while making an update
    boolean existsByEmailAndIdNot(String email, UUID id);
}
