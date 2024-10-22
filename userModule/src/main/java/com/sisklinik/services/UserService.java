package com.sisklinik.services;

import java.util.List;

import com.sisklinik.dtos.UserappDto;
import com.sisklinik.entities.Userapp;
import com.sisklinik.params.input.UserappParamsInput;

public interface UserService {
	
	public List<UserappDto> findAllUsers();
	
	public Userapp verifyUserapp(String username);
	
	public boolean verifyUniqueCf(String cf);
	
	public UserappDto insertNewUser(UserappParamsInput userappInput);
	
	public UserappDto updateUser(UserappParamsInput userappInput);
	
	public void deleteUser(Integer id);

}
