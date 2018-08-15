package com.example.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "patient")
public class Patient implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	@NotNull(message="Please enter a ID")
	private Integer patientId;
	
	@Column
	private String patientName;

	@Column
	private String destPlace;

	@Column
	private String time;

	@Column
	private String description;

	@Column
	private String age;
	
	@Column
	private String gender;
	
	
	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDestPlace() {
		return destPlace;
	}

	public void setDestPlace(String destPlace) {
		this.destPlace = destPlace;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

	public Patient() {
		
	}

	public Patient(int patientId, String patientName, String destPlace, String time, String description, String age,
			String gender) {
		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.destPlace = destPlace;
		this.time = time;
		this.description = description;
		this.age = age;
		this.gender = gender;
	}

	

	
}
