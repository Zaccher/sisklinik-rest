package com.sisklinik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sisklinik.exceptions.AuthenticationException;
import com.sisklinik.security.JwtConfig;
import com.sisklinik.security.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
 
@RestController
@Log
public class JwtAuthenticationRestController 
{

//	@Value("${sicurezza.header}")
//	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	@Qualifier("JwtAuthUserDetailsService")
	private UserDetailsService userDetailsService;
	
	// Endpoint per la generazione del token
	@PostMapping(value = "${sicurezza.uri}")
	@SneakyThrows
	public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) 
	{
		log.info("Autenticazione e Generazione Token");
		
		String token = "";
		
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
	    
	    if(authentication.isAuthenticated()) {

			try {
				
				token = jwtTokenUtil.generateToken(userDetails);
				
			}catch (Exception e) {
				System.out.println("Prova");
			}
		
			log.warning(String.format("Token %s", token));
	    }

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}

	// Endpoint per il refresh del token
	@GetMapping(value = "${sicurezza.refresh}")
	@SneakyThrows
	public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) 
	{
		log.info("Tentativo Refresh Token");
		String authToken = request.getHeader(jwtConfig.getHeader());
		
		if (authToken == null)
		{
			throw new Exception("Token assente o non valido!");
		}
		
		final String token = authToken.substring(7); // Devo togliergli il Bearer
		
		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			
			log.warning(String.format("Refreshed Token %s", refreshedToken));
			
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
		} 
		else 
		{
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) 
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

}
