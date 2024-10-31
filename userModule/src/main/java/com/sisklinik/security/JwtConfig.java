package com.sisklinik.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtConfig {
	
	@Value("${sicurezza.header}")
	private String header;
	
	@Value("${sicurezza.secret}")
	private String secret;
}
