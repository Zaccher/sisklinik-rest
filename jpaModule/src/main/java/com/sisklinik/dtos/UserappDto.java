package com.sisklinik.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserappDto {
	
	private String id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String fiscalCode;
	private String mailAddress;

}
