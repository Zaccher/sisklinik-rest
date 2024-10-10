package com.sisklinik.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerErrorException extends Exception {

	private static final long serialVersionUID = -1542202979655806355L;
	
	private String messaggio = "Errore interno del server. Contattare l'assistenza!";
	
	public InternalServerErrorException()
	{
		super();
	}
	
	public InternalServerErrorException(String messaggio)
	{
		super(messaggio);
		this.messaggio = messaggio;
	}

}
