package com.bytatech.ayoos.repository;

import com.bytatech.ayoos.domain.Patient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
Patient findByIdpCode(String idpCode);
}
