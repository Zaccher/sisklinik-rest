package com.sisklinik.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisklinik.dtos.EventDto;
import com.sisklinik.dtos.UserappDto;
import com.sisklinik.entities.Event;
import com.sisklinik.entities.Userapp;
import com.sisklinik.mappers.UserMapper;
import com.sisklinik.params.input.UserappParamsInput;
import com.sisklinik.repository.UserappRepository;
import com.sisklinik.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserappRepository ur;
	
	@Autowired
	UserMapper mapper;
	
	@Override
	public List<UserappDto> findAllUsers() {

		List<Userapp> users= ur.findAll();
		
		return mapper.listUserappToListUserappDto(users);
	}

	@Override
	public UserappDto verifyUserapp(String username, String password) {

		Userapp userapp = ur.findByUsernameAndPassword(username, password);
		
		if(userapp != null) {
			
			return mapper.userappToUserappDto(userapp);
			
		}
		
		return null;
	}

	@Override
	public UserappDto insertNewUser(UserappParamsInput userappInput) {

		UserappDto userappDto = null;
		
		Userapp userapp = mapper.userappParamsInputToUserapp(userappInput);
		
		ur.save(userapp);
		
		userappDto = mapper.userappToUserappDto(userapp);
		
		return userappDto;
		
	}

	@Override
	public UserappDto updateUser(UserappParamsInput userappInput) {
		
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
			userapp.setBirthDate(userappInput.getBirthDate());
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

}
