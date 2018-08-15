package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

import com.example.test.model.Patient;


public interface PatientRepository extends CrudRepository<Patient, Integer> {
	List<Patient> findByPatientName(String patientName);

	void save(Optional<Patient> patientEdit);
}
