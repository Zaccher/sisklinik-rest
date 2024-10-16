package com.sisklinik.params.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientParamsInput {

	private String id;
	
	@NotNull(message = "{NotNull.Patient.name.Validation}")
	@NotBlank(message = "{NotBlank.Patient.name.Validation}")
	private String name;
	
	@NotNull(message = "{NotNull.Patient.surname.Validation}")
	@NotBlank(message = "{NotBlank.Patient.surname.Validation}")
	private String surname;
	
	@NotNull(message = "{NotNull.Patient.gender.Validation}")
	@NotBlank(message = "{NotBlank.Patient.gender.Validation}")
	private String gender;
	
	@NotNull(message = "{NotNull.Patient.fiscal_code.Validation}")
	@NotBlank(message = "{NotBlank.Patient.fiscal_code.Validation}")
	private String fiscal_code;
	
	@NotNull(message = "{NotNull.Patient.birth_time.Validation}")
	@NotBlank(message = "{NotBlank.Patient.birth_time.Validation}")
	private String birth_time;
	
	@NotNull(message = "{NotNull.Patient.birth_place.Validation}")
	@NotBlank(message = "{NotBlank.Patient.birth_place.Validation}")
	private String birth_place;
	
	private String residence_address;
	private String residence_postcode;
	private String residence_municipality;
	private String residence_district;
	private String home_address;
	private String home_postcode;
	private String home_municipality;
	private String home_district;
	
	@NotNull(message = "{NotNull.Patient.personal_phone.Validation}")
	@NotBlank(message = "{NotBlank.Patient.personal_phone.Validation}")
	private String personal_phone;
	
	private String home_phone;
	
	@NotNull(message = "{NotNull.Patient.mail_address.Validation}")
	@NotBlank(message = "{NotBlank.Patient.mail_address.Validation}")
	private String mail_address;
	
}
