package com.sisklinik.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sisklinik.entities.Userapp;
import com.sisklinik.entities.UserappRole;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserConfig Config;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {

		String errMsg = "";
		
		if(username == null || username.length() < 5) {
			
			errMsg = "Username assente o non valido";
			log.warning(errMsg);
			throw new UsernameNotFoundException(errMsg);
		}
		
		Userapp userapp = this.getHttpValue(username);
		
		if (userapp == null)
		{
			errMsg = String.format("Utente %s non Trovato!!", username);
			
			log.warning(errMsg);
			
			throw new UsernameNotFoundException(errMsg);
		}
		
		// Andremo a creare l'utente spring security in base allo Userapp ritornato dal servizio
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(userapp.getUsername());
		builder.disabled((userapp.isVisible() ? false : true));
		builder.password(new BCryptPasswordEncoder().encode(userapp.getPassword()));
		
		List<String> listaRuoli = new ArrayList<>();
		
		if(!userapp.getUserappRoles().isEmpty()) {
			for(UserappRole ur: userapp.getUserappRoles()) {
				listaRuoli.add(ur.getRole().getName());
			}
		}		
		
		String[] profili = listaRuoli
				 .stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		
		builder.authorities(profili); 
		
		return builder.build();
	}
	
	private Userapp getHttpValue(String username) {
		
		URI url = null;

		try {
			
			String srvUrl = Config.getSrvUrl();

			url = new URI(srvUrl + username);
			
		} catch (URISyntaxException e) {
			 
			e.printStackTrace();
		}
		
		/* Bisogna far itneragire i due servizi in modalità sincrona. Per fare ciò, si
		 * Utilizza il RestTemplate 
		 */
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(Config.getUserId(), Config.getPassword()));
		
		Userapp userapp = null;
		
		try {
			
			userapp = restTemplate.getForObject(url, Userapp.class);	
			
		} catch (Exception e) {
			
			String ErrMsg = String.format("Connessione al servizio di autenticazione non riuscita!!");
			
			log.warning(ErrMsg);
			
		}
		
		return userapp;
	}

}
