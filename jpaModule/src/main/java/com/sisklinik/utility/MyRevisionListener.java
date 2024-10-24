package com.sisklinik.utility;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sisklinik.entities.MyRevision;



public class MyRevisionListener implements RevisionListener {

	/* Questo metodo viene chiamato ogni volta che si richiama un salvataggio di qualcosa sul db
	 * Per Salvataggio s'intende un inserimento, una modifica o una cancellazione
	 */
	@Override
	public void newRevision(Object revisionEntity) {

		MyRevision rev = (MyRevision) revisionEntity;
        rev.setUserName(getUserName());
		
	}
	
	/* Questo metodo restituisce la username che ha effettuato l'operazione.
	 * La username viene ripresa dal contesto di Spring Security visto che
	 * il Web Service utilizza la BasicAuth 
	 */
	private String getUserName() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		return currentPrincipalName;
	}

}
