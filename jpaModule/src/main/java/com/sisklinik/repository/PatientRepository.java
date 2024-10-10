package com.sisklinik.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sisklinik.entities.Patient;
import com.sisklinik.repository.custom.PatientRepositoryCustom;

public interface PatientRepository extends JpaRepository<Patient, Integer>, PatientRepositoryCustom {

}
