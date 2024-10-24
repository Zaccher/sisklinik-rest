package com.sisklinik.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisklinik.dtos.AgendaResourceDto;
import com.sisklinik.dtos.EventDto;
import com.sisklinik.dtos.PatientDto;

import com.sisklinik.entities.AgendaResource;
import com.sisklinik.entities.Event;
import com.sisklinik.entities.Patient;
import com.sisklinik.mappers.AgendaMapper;
import com.sisklinik.params.input.EventParamsInput;
import com.sisklinik.params.input.PatientParamsInput;
import com.sisklinik.repository.AgendaResourceRepository;
import com.sisklinik.repository.EventRepository;
import com.sisklinik.repository.PatientRepository;
import com.sisklinik.services.AgendaService;


@Service
public class AgendaServiceImpl implements AgendaService {
	
	@Autowired
    EventRepository er;
	
	@Autowired
	AgendaResourceRepository arr;
	
	@Autowired
	PatientRepository pr;
	
	@Autowired
	AgendaMapper mapper;

	@Override
	public List<AgendaResourceDto> findAllResources() {
		
		List<AgendaResource> resources= arr.findAll();
		
		return mapper.listAgendaResourceToListAgendaResourceDto(resources);
		
	}

	@Override
	public List<EventDto> findAllEvents(LocalDateTime start, LocalDateTime end) {
		
		List<Event> listaEvent = er.findBetween(start, end);
		
		return mapper.convertListEventToEventDto(listaEvent);
		
	}
	
	@Override
	public EventDto findEventById(Integer id) {

		Optional<Event> event = er.findById(id); 
		
		if(event.isPresent()) {
			
			return mapper.eventToEventDto(event.get());
			
		}else {
			
			return null;
			
		}
	}
	
	@Override
	public EventDto insertNewEvent(EventParamsInput eventInput) {
		
		// Facciamo il find della resource
		Optional<AgendaResource> agendaResource = arr.findById(Integer.parseInt(eventInput.getResource()));
		
		// Facciamo il find del Patient
		Optional<Patient> patient = pr.findById(Integer.parseInt(eventInput.getPatient()));
		
		EventDto eventDto = null;
		
		if(agendaResource.isPresent() && patient.isPresent()) {
			
			Event newEvent = new Event();

			newEvent.setStart(eventInput.getStart());
			newEvent.setEnd(eventInput.getEnd());
			newEvent.setBarColor(eventInput.getBarColor());
			newEvent.setText(eventInput.getText());
			newEvent.setNote(eventInput.getNote());
			newEvent.setVisible(true);
			// Setto la Resource
			newEvent.setResource(agendaResource.get());
			// Setto il Patient
			newEvent.setPatient(patient.get());

			// Salvataggio newEvent sul DB
	        er.save(newEvent);
	        
	        // Convertiamo l'event appena salvato in un DTO
	        eventDto = mapper.eventToEventDtoWithoutChangeDate(newEvent);
		}
		
        return eventDto;
        
	}
	
	@Override
	public EventDto updateEvent(EventParamsInput eventInput) {

		Optional<Event> returnFind = er.findById(eventInput.getId());
		
		EventDto eventoDto = null;
		
		if(returnFind.isPresent()) {
			
			Event evento = returnFind.get();
			evento.setStart(eventInput.getStart());
			evento.setEnd(eventInput.getEnd());
			evento.setBarColor(eventInput.getBarColor());
			evento.setText(eventInput.getText());
			evento.setNote(eventInput.getNote());
			
			// Salvataggio Event sul DB
			er.save(evento);
			
			// Convertiamo l'event appena salvato in un DTO
			eventoDto = mapper.eventToEventDtoWithoutChangeDate(evento);
		}
		
		return eventoDto;
		
	}
	
	@Override
	public void deleteEvent(Integer id, String username) {
		
		Optional<Event> event = er.findById(id);
		if(event.isPresent()) {
			
			// Mettiamo il visible a false
			Event eventPresent = event.get();
			eventPresent.setVisible(false);
			
			// Salviamo la cancellazione logica dell'event
			er.save(eventPresent);
			
		}

	}

	@Override
	public List<PatientDto> findPatientsByParams(PatientParamsInput params) {
		
		List<PatientDto> listaPatient = pr.getPatientByParameters(params);
				
		return listaPatient;
	}
	
	@Override
	public PatientDto findPatientById(Integer id) {
		
		Optional<Patient> patient = pr.findById(id); 
		
		if(patient.isPresent()) {
			
			return mapper.patientToPatientDto(patient.get());
			
		}else {
			
			return null;
			
		}
	}

	@Override
	public PatientDto insertNewPatient(PatientParamsInput params) {
				
		PatientDto patientDto = null;
		
		params.setBirth_time(params.getBirth_time().concat(" 00:00"));
			
		Patient newPatient = mapper.patientParamsInputToPatient(params);

		// Salvataggio newEvent sul DB
		pr.save(newPatient);
        
        // Convertiamo l'event appena salvato in un DTO
		patientDto = mapper.patientToPatientDto(newPatient);
		
        return patientDto;
	}

	

	

}
