package com.sisklinik.repository.custom;

import java.util.List;


import com.sisklinik.dtos.PatientDto;
import com.sisklinik.params.input.PatientParamsInput;



public interface PatientRepositoryCustom {

	List<PatientDto> getPatientByParameters(PatientParamsInput params);
}
