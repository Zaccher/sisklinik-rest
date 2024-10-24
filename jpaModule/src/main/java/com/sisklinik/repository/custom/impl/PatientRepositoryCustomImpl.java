package com.sisklinik.repository.custom.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.sisklinik.dtos.PatientDto;
import com.sisklinik.params.input.PatientParamsInput;
import com.sisklinik.repository.custom.PatientRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class PatientRepositoryCustomImpl implements PatientRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientDto> getPatientByParameters(PatientParamsInput params) {
		
		List<PatientDto> finalResult = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
        sb.append("SELECT "
        		+ "id, name, surname, gender, fiscal_code, birth_time, "
        		+ "birth_place, residence_address "
        		+ "FROM patient WHERE 1=1 ");

        if(params.getName() !=null && StringUtils.isNotEmpty(params.getName())) {
            sb.append(" AND UPPER(name) = UPPER(:name) ");
        }
        
        if(params.getSurname() !=null && StringUtils.isNotEmpty(params.getSurname())) {
            sb.append(" AND UPPER(surname) = UPPER(:surname) ");
        }
        
        if(params.getGender() !=null && StringUtils.isNotEmpty(params.getGender())) {
            sb.append(" AND UPPER(gender) = UPPER(:gender) ");
        }
        
        if(params.getFiscal_code() !=null && StringUtils.isNotEmpty(params.getFiscal_code())) {
            sb.append(" AND UPPER(fiscal_code) = UPPER(:fiscalCode) ");
        }
        
        if(params.getBirth_time() != null && StringUtils.isNotEmpty(params.getBirth_time())) {
        	sb.append(" AND birth_time = to_date(:birthDate,'YYYY-MM-dd') ");
        }
        
        if(params.getId() != null && StringUtils.isNotEmpty(params.getId())) {
        	sb.append(" AND id = :id ");
        }

        Query query = entityManager.createNativeQuery(sb.toString());

        if(params.getName() !=null && StringUtils.isNotEmpty(params.getName())) {
            query.setParameter("name", params.getName());
        }
        
        if(params.getSurname() !=null && StringUtils.isNotEmpty(params.getSurname())) {
            query.setParameter("surname", params.getSurname());
        }
        
        if(params.getGender() !=null && StringUtils.isNotEmpty(params.getGender())) {
            query.setParameter("gender", params.getGender());
        }
        
        if(params.getFiscal_code() !=null && StringUtils.isNotEmpty(params.getFiscal_code())) {
            query.setParameter("fiscalCode", params.getFiscal_code());
        }
        
        if(params.getBirth_time() !=null && StringUtils.isNotEmpty(params.getBirth_time())) {
            query.setParameter("birthDate", params.getBirth_time());
        }
        
        if(params.getId() !=null && StringUtils.isNotEmpty(params.getId())) {
            query.setParameter("id", Integer.parseInt(params.getId()));
        }
        
        List<Object[]> listaResult = query.getResultList();
        
        finalResult = convertListArrayObjectToListPatientDto(listaResult);
        
        return finalResult;
	}
	
	private List<PatientDto> convertListArrayObjectToListPatientDto(List<Object[]> listaResult) {
		
		// Lista da restituire alla fine dell'elaborazione
		List<PatientDto> finalResult = new ArrayList<>();
		
		if(listaResult != null && !listaResult.isEmpty()) {
        	
        	//Conversione della Collection
	        for(Object[] obj : listaResult) {
	        	
	        	PatientDto pdto = Optional.of(obj).map(o -> { 
	        		
	        		PatientDto tmp = new PatientDto(); 
	        	    
	        		// Id
	        		if(o[0] != null)
	        			tmp.setId(((Integer) o[0]).toString().trim());
	        	     
	        	    // Name
	        	    if(o[1] != null)
	        	    	tmp.setName(((String) o[1]).trim());
	        	     
	        	     // Surname
	        	     if(o[2] != null)
	        	    	tmp.setSurname(((String) o[2]).trim());
	        	     
	        	     // Gender
	        	     if(o[3] != null)
	        	    	tmp.setGender(((String) o[3]));
	        	     
	        	     // Fiscal code
	        	     if(o[4] != null)
	        	        tmp.setFiscal_code(((String) o[4]).trim());
	        	     
	        	     // Birth time
	        	     if(o[5] != null) {
	        	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        	    	LocalDateTime ldt = ((java.sql.Timestamp) o[5]).toLocalDateTime();
	        	        tmp.setBirth_time(ldt.format(formatter));
	        	     }
	        	     
	        	     // Birth place
	        	     if(o[6] != null)
	        	    	tmp.setBirth_place(((String) o[6]).trim());
	        	     
	        	     // Residence address
	        	     if(o[7] != null)
	        	        tmp.setResidence_address(((String) o[7]).trim());
	        	     return tmp; 
	        	}).get();
	        	
	        	finalResult.add(pdto);
	        }
	        
        }
		
		return finalResult;

	}

}
