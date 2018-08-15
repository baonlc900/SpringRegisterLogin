package com.example.test.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.test.model.Patient;
import com.example.test.service.PatientService;

@Component
public class PatientValidator implements Validator {
    @Autowired
    private PatientService patientService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Patient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Patient patient = (Patient) o;

       
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientId", "NotEmpty");
        if(patient.getPatientId()== null) {
        	errors.rejectValue("patientId", "Size.patientForm.patientId");
        }

        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientName", "NotEmpty");
        if(patient.getPatientName().equals("")) {
        	errors.rejectValue("patientName", "Size.patientForm.patientName");
        }
       
    }
}
