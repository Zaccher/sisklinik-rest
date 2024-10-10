package com.sisklinik.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class AgendaUtility {
	
	@Autowired
	ResourceBundleMessageSource errMessage;

	public String SortErrors(List<FieldError> errors) {
		String msgErr = "";
		
		List<String> valErrors = new ArrayList<String>();
		
		for (FieldError item : errors)
		{
			valErrors.add(errMessage.getMessage(item, LocaleContextHolder.getLocale()) + ". ");
		}
		
		Collections.sort(valErrors);
		
		for(String str: valErrors) msgErr += str;
		
		return msgErr;
	}
	
}
