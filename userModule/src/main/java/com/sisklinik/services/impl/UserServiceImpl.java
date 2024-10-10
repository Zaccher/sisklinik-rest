package com.sisklinik.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisklinik.dtos.UserappDto;
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

}
