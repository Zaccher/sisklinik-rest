package com.sisklinik.params.output;

import com.sisklinik.dtos.PatientDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientOutput extends BasicOutput {
	
	private PatientDto patientDto;

}
