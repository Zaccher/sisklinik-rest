package com.sisklinik.services.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisklinik.dtos.UserappDto;
import com.sisklinik.entities.AgendaResource;
import com.sisklinik.entities.Userapp;
import com.sisklinik.mappers.UserMapper;
import com.sisklinik.params.input.UserappParamsInput;
import com.sisklinik.repository.AgendaResourceRepository;
import com.sisklinik.repository.UserappRepository;
import com.sisklinik.services.UserService;

import lombok.SneakyThrows;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserappRepository ur;
	
	@Autowired
	AgendaResourceRepository ar;
	
	@Autowired
	UserMapper mapper;
	
	@Override
	public List<UserappDto> findAllUsers() {

		List<Userapp> users= ur.findAll();
		
		return mapper.listUserappToListUserappDto(users);
	}

	@Override
	public Userapp verifyUserapp(String username) {

		return ur.findByUsername(username);
		
	}

	@SneakyThrows
	@Override
	public UserappDto insertNewUser(UserappParamsInput userappInput) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		UserappDto userappDto = null;
		
		Userapp userapp = mapper.userappParamsInputToUserapp(userappInput);
		
		if(userappInput.getBirthDate() != null && !userappInput.getBirthDate().isEmpty()) {
			userapp.setBirthDate(formatter.parse(userappInput.getBirthDate()));
		}
		
		// Memoriziamo lo userapp
		ur.save(userapp);
		
		// se a FE abbiamo selezionato anche la risorsa
		if(userappInput.getCheckResource().equals("T")) {
			
			AgendaResource agendaResource = new AgendaResource();
			agendaResource.setAlias(userappInput.getAlias());
			agendaResource.setIcon("pat-blue.jpg");
			agendaResource.setVisible(true);
			if(userappInput.getFile() != null) {
				agendaResource.setDisplayPicture(userappInput.getFile().getBytes());
			}
			agendaResource.setUserapp(userapp);
			
			// Memoriziamo l'agendaResource
			ar.save(agendaResource);
			
		}
		
		userappDto = mapper.userappToUserappDto(userapp);
		
		return userappDto;
		
	}

	@Override
	@SneakyThrows
	public UserappDto updateUser(UserappParamsInput userappInput) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Optional<Userapp> returnFind = ur.findById(userappInput.getId());
		
		UserappDto userappDto = null;
		
		if(returnFind.isPresent()) {
			
			//Setting delle modifiche
			Userapp userapp = returnFind.get();
			userapp.setUsername(userappInput.getUsername());
			userapp.setPassword(userappInput.getPassword());
			userapp.setName(userappInput.getName());
			userapp.setSurname(userappInput.getSurname());
			userapp.setFiscalCode(userappInput.getFiscalCode());
			userapp.setBirthDate(formatter.parse(userappInput.getBirthDate()));
			userapp.setBirthPlace(userappInput.getBirthPlace());
			userapp.setAddress(userappInput.getAddress());
			userapp.setPostcode(userappInput.getPostcode());
			userapp.setMunicipality(userappInput.getMunicipality());
			userapp.setDistrict(userappInput.getDistrict());
			userapp.setPersonalPhone(userappInput.getPersonalPhone());
			userapp.setHomePhone(userappInput.getHomePhone());
			userapp.setMailAddress(userappInput.getMailAddress());
			
			// Salvataggio Event sul DB
			ur.save(userapp);
			
			// Convertiamo l'event appena salvato in un DTO
			userappDto = mapper.userappToUserappDto(userapp);
		}
		
		return userappDto;
	}

	@Override
	public void deleteUser(Integer id) {

		Optional<Userapp> userapp = ur.findById(id);
		if(userapp.isPresent()) {
			
			// Mettiamo il visible a false
			Userapp userappPresent = userapp.get();
			userappPresent.setVisible(false);
			
			// Salviamo la cancellazione logica dell'event
			ur.save(userappPresent);
			
		}
		
	}

	@Override
	public boolean verifyUniqueCf(String cf) {
		
		Userapp userapp = ur.findByFiscalCode(cf);
		
		if(userapp != null) {
			return true;
		}else {
			return false;
		}
		
	}

}
