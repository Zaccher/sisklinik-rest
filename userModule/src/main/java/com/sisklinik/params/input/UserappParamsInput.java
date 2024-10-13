package com.sisklinik.params.input;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserappParamsInput {
	
	private Integer id;
	
	@NotNull(message = "{NotNull.Userapp.username.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.username.Validation}")
	private String username;
	
	@NotNull(message = "{NotNull.Userapp.password.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.password.Validation}")
	private String password;
	
	@NotNull(message = "{NotNull.Userapp.name.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.name.Validation}")
	private String name;
	
	@NotNull(message = "{NotNull.Userapp.surname.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.surname.Validation}")
	private String surname;
	
	@NotNull(message = "{NotNull.Userapp.fiscalCode.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.fiscalCode.Validation}")
	private String fiscalCode;
	
	private Date birthDate;
	
	private String birthPlace;
	
	private String address;
	
	private String postcode;
	
	private String municipality;
	
	private String district;
	
	private String personalPhone;
	
	private String homePhone;
	
	@NotNull(message = "{NotNull.Userapp.mailAddress.Validation}")
	@NotBlank(message = "{NotBlank.Userapp.mailAddress.Validation}")
	private String mailAddress;
	
	private boolean checkResource;
	private String alias;

}
