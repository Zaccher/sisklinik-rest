package com.sisklinik.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component 
@Getter
@Setter
public class UserConfig 
{
	@Value("${userapp.srvUrl}") // Mi riprendo la proprietà userapp.srvUrl dal file application.properties
	private String srvUrl;
	
	@Value("${userapp.userId}")
	private String userId;
	
	@Value("${userapp.password}")
	private String password;
}
