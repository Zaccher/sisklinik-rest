package com.sisklinik.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sisklinik.dtos.AgendaResourceDto;
import com.sisklinik.dtos.EventDto;
import com.sisklinik.dtos.PatientDto;

import com.sisklinik.entities.AgendaResource;
import com.sisklinik.entities.Event;
import com.sisklinik.entities.Patient;
import com.sisklinik.params.input.PatientParamsInput;

@Mapper
public interface AgendaMapper {

	AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);
	
	// Questo viene richiamato nella lettura generale delle risorse mediche da FE
	@Mapping(expression = "java(agendaResource.getId().toString())", target = "id")
	@Mapping(source = "alias", target = "name")
	@Mapping(source = "icon", target = "icon")
	AgendaResourceDto agendaResourceToAgendaResourceDto(AgendaResource agendaResource);
	
	// Queste converte una lista di AgendaResource in una lista di AgendaResourceDto
	List<AgendaResourceDto> listAgendaResourceToListAgendaResourceDto(List<AgendaResource> lista);
	
	// Questo serve per l'inserimento o la modifica di un evento
	@Mapping(source = "id", target = "id")
	@Mapping(source = "start", target = "start")
	@Mapping(source = "end", target = "end")
	@Mapping(source = "barColor", target = "barColor")
	@Mapping(source = "note", target = "note")
	@Mapping(source = "text", target = "text")
	@Mapping(target = "resource", expression = "java(\"\"+event.getResource().getId())")
	@Mapping(target = "patient", expression = "java(\"\"+event.getPatient().getId())")
	@Mapping(target = "resizeDisabled", constant = "true")
	@Mapping(target = "moveDisabled", constant = "true")
	EventDto eventToEventDtoWithoutChangeDate(Event event);
	
	// Questo serve per la lettura generale degli eventi da FE
	@Mapping(source = "id", target = "id")
	@Mapping(target = "start", expression = "java(plusHoursToDate(event.getStart()))")
	@Mapping(target = "end", expression = "java(plusHoursToDate(event.getEnd()))")
	@Mapping(source = "barColor", target = "barColor")
	@Mapping(source = "note", target = "note")
	@Mapping(source = "text", target = "text")
	@Mapping(target = "resource", expression = "java(\"\"+event.getResource().getId())")
	@Mapping(target = "patient", expression = "java(\"\"+event.getPatient().getId())")
	@Mapping(target = "resizeDisabled", constant = "true")
	@Mapping(target = "moveDisabled", constant = "true")
	EventDto eventToEventDto(Event event);
	
	//Metodo che toglie 7 ore a causa di un bug nel FE
	default LocalDateTime plusHoursToDate(LocalDateTime ldt) {
		
		return ldt.plusHours(-7);
		
	}
	
	// Converte una lista di Event in una lista di EventDto richiamando la logica che toglie 7 ore
	default List<EventDto> convertListEventToEventDto(List<Event> listaEvent) {
		
		if (listaEvent == null) {
            return null;
        }
		
		List<EventDto> list = new ArrayList<EventDto>(listaEvent.size());
			
		for(Event event : listaEvent) {
			
			list.add(eventToEventDto(event));
			
		}
		
		return list;
	}
	
	default LocalDateTime stringToLocalDateTime(String data) {
		if(data != null && !data.equals("")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			return LocalDateTime.parse(data, formatter);
		}else {
			return null;
		}
	}
	
	default String localDateTimeToString(LocalDateTime data) {
		if(data != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return data.format(formatter);
		}else {
			return "";
		}
	}
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "name", target = "name")
	@Mapping(source = "surname", target = "surname")
	@Mapping(source = "gender", target = "gender")
	@Mapping(source = "fiscal_code", target = "fiscalCode")
	@Mapping(target = "birthTime", expression = "java(stringToLocalDateTime(params.getBirth_time()))")
	@Mapping(source = "birth_place", target = "birthPlace")
	@Mapping(source = "residence_address", target = "residenceAddress")
	@Mapping(source = "residence_postcode", target = "residencePostcode")
	@Mapping(source = "residence_municipality", target = "residenceMunicipality")
	@Mapping(source = "residence_district", target = "residenceDistrict")
	@Mapping(source = "home_address", target = "homeAddress")
	@Mapping(source = "home_postcode", target = "homePostcode")
	@Mapping(source = "home_municipality", target = "homeMunicipality")
	@Mapping(source = "home_district", target = "homeDistrict")
	@Mapping(source = "personal_phone", target = "personalPhone")
	@Mapping(source = "home_phone", target = "homePhone")
	@Mapping(source = "mail_address", target = "mailAddress")
	@Mapping(target = "visible", ignore = true)
	Patient patientParamsInputToPatient(PatientParamsInput params);
	
	@Mapping(target = "id", expression = "java(patient.getId().toString())")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "surname", target = "surname")
	@Mapping(source = "gender", target = "gender")
	@Mapping(source = "fiscalCode", target = "fiscal_code")
	@Mapping(target = "birth_time", expression = "java(localDateTimeToString(patient.getBirthTime()))")
	@Mapping(source = "birthPlace", target = "birth_place")
	@Mapping(source = "residenceAddress", target = "residence_address")
	PatientDto patientToPatientDto(Patient patient);
}
