package com.sisklinik.services;

import java.time.LocalDateTime;
import java.util.List;

import com.sisklinik.dtos.AgendaResourceDto;
import com.sisklinik.dtos.EventDto;
import com.sisklinik.dtos.PatientDto;
import com.sisklinik.params.input.EventParamsInput;
import com.sisklinik.params.input.PatientParamsInput;


public interface AgendaService {

	public List<AgendaResourceDto> findAllResources();
	
	public List<EventDto> findAllEvents(LocalDateTime start, LocalDateTime end);
	
	public EventDto findEventById(Integer id);
	
	public EventDto insertNewEvent(EventParamsInput eventInput);
	
	public EventDto updateEvent(EventParamsInput eventInput);
	
	public void deleteEvent(Integer id);
	
	public List<PatientDto> findPatientsByParams(PatientParamsInput params);
	
	public PatientDto findPatientById(Integer id);
	
	public PatientDto insertNewPatient(PatientParamsInput patientInput);
	
	public PatientDto updatePatient(PatientParamsInput patientInput);
}
