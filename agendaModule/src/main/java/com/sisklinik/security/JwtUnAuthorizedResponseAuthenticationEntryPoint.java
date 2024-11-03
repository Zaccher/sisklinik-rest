package com.sisklinik.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sisklinik.dtos.InfoMsg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Component
@Log
public class JwtUnAuthorizedResponseAuthenticationEntryPoint  extends BasicAuthenticationEntryPoint
{
	private static String REALM = "REAME";
	
	@Override
	@SneakyThrows
	public void commence(
			final HttpServletRequest request, 
			final HttpServletResponse response,
			final AuthenticationException authException) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Gson gson = new Gson();
		
		InfoMsg infoMsg = new InfoMsg(formatter.format(new Date()), "Token di autorizzazione assente o non valido!");
		
		log.warning("Errore Sicurezza: " + authException.getMessage());
		
		// Authentication failed, send error response.
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
		
		response.getOutputStream().println(gson.toJson(infoMsg));

	}
	
	@Override
	public void afterPropertiesSet()
	{
		setRealmName(REALM);
		super.afterPropertiesSet();
	}
}
