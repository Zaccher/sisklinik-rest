package com.sisklinik.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sisklinik.dtos.AgendaResourceDto;
import com.sisklinik.dtos.EventDto;
import com.sisklinik.dtos.PatientDto;

import com.sisklinik.exceptions.BindingException;
import com.sisklinik.exceptions.InternalServerErrorException;
import com.sisklinik.params.input.EventParamsInput;
import com.sisklinik.params.input.PatientParamsInput;
import com.sisklinik.params.output.EventOutput;
import com.sisklinik.params.output.PatientOutput;
import com.sisklinik.services.AgendaService;
import com.sisklinik.utility.AgendaUtility;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
//@CrossOrigin("http://localhost:4200/") // questo ora viene gestito dallo spring security - vedere classe SecurityConfiguration.java
@RequestMapping("api")
public class AgendaController {

	@Autowired
	AgendaService as;
	
	@Autowired
	AgendaUtility agendaUtility;
	
	@SneakyThrows // questa annotation serve per il reminder delle eccezioni senza utilizzare altro nei metodi
	@GetMapping(value = "/getResources", produces = "application/json")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	ResponseEntity<List<AgendaResourceDto>> getAllResources() {
		
		List<AgendaResourceDto> listaResult = new ArrayList<>();
		
		try {
			
			listaResult = as.findAllResources();
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getAllResources");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
			
		}
		
		return new ResponseEntity<List<AgendaResourceDto>>(listaResult, HttpStatus.OK);
    }
	
	@SneakyThrows
	@GetMapping(value = "/getEvents", produces = "application/json")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	ResponseEntity<List<EventDto>> getEvents(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
		
		List<EventDto> listaResult = new ArrayList<>();
		
		try {
			
			listaResult = as.findAllEvents(start, end);
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getEvents");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<List<EventDto>>(listaResult, HttpStatus.OK);
    }
	
	@SneakyThrows
	@GetMapping(value = "/getEventById", produces = "application/json")
	ResponseEntity<EventDto> getEventById(@RequestParam("id") String id) {
		
		EventDto result = new EventDto();
		
		try {
			
			if(StringUtils.isEmpty(id.trim())) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getEventById");
				log.warning(errMsg);
				throw new BindingException(errMsg);
				
			} else {
			
				result = as.findEventById(Integer.parseInt(id));
				
				if(result == null) {
					
					String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
							+ "- Non è stato possibile recuperare l'evento! - getEventById");
					
					log.warning(errMsg);
					
					throw new InternalServerErrorException(errMsg);
					
				}
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getEventById");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<EventDto>(result, HttpStatus.OK);
	}
	
	@SneakyThrows
	@PostMapping(value = "/getPatients", consumes = "application/json", produces = "application/json")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	ResponseEntity<List<PatientDto>> getPatientsByParams(@RequestBody PatientParamsInput params) {
		
		List<PatientDto> listaResult = new ArrayList<>();
		
		try {
			
			listaResult = as.findPatientsByParams(params);
			
		}catch (Exception e) {
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getPatients");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<List<PatientDto>>(listaResult, HttpStatus.OK);
	}
	
	@SneakyThrows
	@GetMapping(value = "/getPatientById", produces = "application/json")
	ResponseEntity<PatientDto> getPatientById(@RequestParam("id") String id) {
		
		PatientDto result = new PatientDto();
		
		try {
			
			if(StringUtils.isEmpty(id.trim())) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getPatientById");
				log.warning(errMsg);
				throw new BindingException(errMsg);
				
			} else {
			
				result = as.findPatientById(Integer.parseInt(id));
				
				if(result == null) {
					
					String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
							+ "- Non è stato possibile recuperare il paziente! - getPatientById");
					
					log.warning(errMsg);
					
					throw new InternalServerErrorException(errMsg);
					
				}
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getPatientById");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<PatientDto>(result, HttpStatus.OK);
	}
	
	@SneakyThrows
	@PostMapping(value = "/event/insert" , produces = "application/json")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    ResponseEntity<EventOutput> insertEvent(@Valid @RequestBody EventParamsInput eventInput, BindingResult bindingResult) {
		
		EventDto eventDto = null;
		EventOutput eventOutput = new EventOutput(); 
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors()) {
			
			String MsgErr = agendaUtility.SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
			
		}
		
		try {
			
			eventDto = as.insertNewEvent(eventInput);
	
			if(eventDto == null) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Inserimento nuovo evento non effettuato! - insertEvent");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
			}else {
				
				eventOutput.setEventDto(eventDto);
				eventOutput.setResult("Evento creato con successo!");
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
					+ "- Eccezione Interna! - insertEvent");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<EventOutput>(eventOutput, HttpStatus.OK);

    }
	
	@SneakyThrows
	@PostMapping(value = "/event/update", produces = "application/json" )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    ResponseEntity<EventOutput> updateEvent(@Valid @RequestBody EventParamsInput eventInput, BindingResult bindingResult) {
		
		EventDto eventDto = null;
		EventOutput eventOutput = new EventOutput(); 
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors()) {
			
			String MsgErr = agendaUtility.SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
			
		}
		
		try {
			
			eventDto = as.updateEvent(eventInput);
	
			if(eventDto == null) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Update evento non riuscito! - updateEvent");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
			}
			else {
				
				eventOutput.setEventDto(eventDto);
				eventOutput.setResult("Evento aggiornato con successo!");
			}
			
		}catch (Exception e) {

			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - "
					+ "Eccezione Interna! - updateEvent");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
			
		}
        
		return new ResponseEntity<EventOutput>(eventOutput, HttpStatus.OK);
    }
	
	@SneakyThrows
	@DeleteMapping(value = "/event/delete")
    @Transactional
    ResponseEntity<EventOutput> deleteEvent(@RequestParam String id, @RequestParam String username) {

		try {
			
			if(StringUtils.isEmpty(id.trim()) || StringUtils.isEmpty(username.trim())) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - deleteEvent");
				log.warning(errMsg);
				throw new BindingException(errMsg);
				
			} else {
			
				as.deleteEvent(Integer.parseInt(id), username);
			}
			
		}catch (Exception e) {

			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - "
					+ "Eccezione Interna! - deleteEvent");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
			
		}
		
		EventOutput eventOutput = new EventOutput(); 
		eventOutput.setResult("Evento cancellato con successo!");

		return new ResponseEntity<EventOutput>(eventOutput, HttpStatus.OK);
		
    }
	
	@SneakyThrows
	@PostMapping(value = "/patient/insert" , produces = "application/json")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    ResponseEntity<PatientOutput> insertPatient(@Valid @RequestBody PatientParamsInput patientInput, BindingResult bindingResult) {
		
		PatientDto patientDto = null;
		PatientOutput patientOutput = new PatientOutput(); 
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors()) {
			
			String MsgErr = agendaUtility.SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
			
		}
		
		try {
			
			patientDto = as.insertNewPatient(patientInput);
	
			if(patientInput == null) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Inserimento nuovo paziente non effettuato! - insertPatient");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
			}else {
				
				patientOutput.setPatientDto(patientDto);
				patientOutput.setResult("Paziente creato con successo!");
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
					+ "- Eccezione Interna! - insertPatient");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<PatientOutput>(patientOutput, HttpStatus.OK);

    }
	
}
