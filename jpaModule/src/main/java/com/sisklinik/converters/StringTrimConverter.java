package com.sisklinik.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/* Questo converter serve per le letture da db 
 * Quando, ad esempio, leggiamo un campo da db di tipo string, 
 * effettuiamo un trim su ciò che leggiamo
 */
@Converter
public class StringTrimConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		
		return attribute;
		
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		
		return dbData.trim();
	}

}
