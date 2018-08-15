package com.example.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.Patient;
import com.example.test.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository repo;

	@Override
	public Iterable<Patient> listAllPatient() {
		// TODO Auto-generated method stub
//		List<Patient> patients = (List<Patient>) repo.findAll();
		return repo.findAll();
	}

	@Override
	public Iterable<Patient> listPatienName(String patientName) {
		// TODO Auto-generated method stub
		return repo.findByPatientName(patientName);
	}

	@Override
	public List<Patient> search(String patientName) {
		// TODO Auto-generated method stub
		return repo.findByPatientName(patientName);
	}

	@Override
	public Optional<Patient> findOne(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void save(Patient contact) {
		// TODO Auto-generated method stub
		repo.save(contact);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
