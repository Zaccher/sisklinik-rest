package com.sisklinik.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sisklinik.dtos.InfoMsg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Component
@Log
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {

	@Override
	@SneakyThrows
	public void handle(HttpServletRequest request, 
			           HttpServletResponse response,
			           AccessDeniedException accessDeniedException) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Gson gson = new Gson();

		InfoMsg infoMsg = new InfoMsg(formatter.format(new Date()), "Privilegi insufficenti. Impossibile Proseguire!");
		
		HttpStatus httpStatus = HttpStatus.FORBIDDEN; //403
		response.setStatus(httpStatus.value());
		response.setContentType("application/json;charset=UTF-8");
		
		response.getOutputStream().println(gson.toJson(infoMsg));
		
	}

}
