package com.example.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Patient;
import com.example.test.model.Response;
import com.example.test.service.PatientService;

@RestController
public class AjaxController {
	
	@Autowired
	PatientService patientService;
	
	@RequestMapping("/listPatient")
	public String ajaxPatient(Model model) {
		return "listPatient";
	}
	
	@RequestMapping(value = "/api/patient/{patientId}", method = RequestMethod.GET)
	public Response getPatient(@PathVariable int patientId) {
		Response response = new Response();
		
		Patient returnPatient = null;
		
		
		Iterable<Patient> listPatient = patientService.listAllPatient();
		for (Patient patient : listPatient) {
//			int i = Integer.valueOf(patient.getPatientId());
            if (patient.getPatientId() == patientId) {
            	returnPatient = patient;
                break;
            }
        }
		
		if (returnPatient == null) {
			response.setResponseStatus(Response.NOT_FOUND);
			response.setResponse("");
		}
		else {
			response.setResponseStatus(Response.OK);
			response.setResponse(returnPatient);
		}
		return response;
	}
}
