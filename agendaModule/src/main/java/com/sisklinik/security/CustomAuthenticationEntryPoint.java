package com.sisklinik.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sisklinik.dtos.InfoMsg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Component
@Log
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	@SneakyThrows
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Gson gson = new Gson();
		
		InfoMsg infoMsg = new InfoMsg(formatter.format(new Date()), "Token di autorizzazione assente o non valido!");
		
		log.warning("Errore Sicurezza: " + authException.getMessage());
		
		// Authentication failed, send error response.
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		response.getOutputStream().println(gson.toJson(infoMsg));
		
	}

}
