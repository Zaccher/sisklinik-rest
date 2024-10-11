package com.sisklinik.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
	
	@ExceptionHandler(InternalServerErrorException.class)
	public final ResponseEntity<ErrorResponse> exceptionInternalServerErrorHandler(Exception ex) {
		ErrorResponse errore = new ErrorResponse();
		
		errore.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setMessage(((InternalServerErrorException) ex.getCause()).getMessage());
		
		return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BindingException.class)
	public ResponseEntity<ErrorResponse> exceptionBindingHandler(Exception ex) {
		ErrorResponse errore = new ErrorResponse();
		
		errore.setCode(HttpStatus.BAD_REQUEST.value());
		errore.setMessage(((BindingException) ex.getCause()).getMessage());
		
		return new ResponseEntity<ErrorResponse>(errore, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
