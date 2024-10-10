package com.sisklinik.services;

import java.util.List;

import com.sisklinik.dtos.UserappDto;

public interface UserService {
	
	public List<UserappDto> findAllUsers();
	
	public UserappDto verifyUserapp(String username, String password);

}
