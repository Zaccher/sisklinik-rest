package com.sisklinik.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtConfig
{
	@Value("${sicurezza.uri}") // Mi riprendo la proprietà userapp.srvUrl dal file application.properties
	private String uri;
	
	@Value("${sicurezza.refresh}")
	private String refresh;
	
	@Value("${sicurezza.header}")
	private String header;
	
	@Value("${sicurezza.prefix}")
	private String prefix;
	
	@Value("${sicurezza.expiration}")
	private int expiration;
	
	@Value("${sicurezza.secret}")
	private String secret;
	
	private Boolean noexpiration;
}
