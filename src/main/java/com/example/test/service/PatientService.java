package com.example.test.service;

import java.util.List;
import java.util.Optional;

import com.example.test.model.Patient;

public interface PatientService {

	Iterable<Patient> listAllPatient();
	Iterable<Patient> listPatienName(String patientName);
	List<Patient> search(String patientName);
	Optional<Patient> findOne (int id);
	void save (Patient contact);
	void delete (int id);
}
