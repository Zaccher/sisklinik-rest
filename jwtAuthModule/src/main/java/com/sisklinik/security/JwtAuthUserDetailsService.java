package com.sisklinik.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sisklinik.entities.Userapp;
import com.sisklinik.entities.UserappRole;
import com.sisklinik.repository.UserappRepository;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@Service("JwtAuthUserDetailsService")
public class JwtAuthUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserappRepository ur;
	
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		
		log.info("Inizio Verifica Username - JwtAuthModule");
		
		String ErrMsg = "";
		
		if (username == null || username.length() < 5) 
		{
			ErrMsg = "Nome utente assente o non valido";
			
			log.warning(ErrMsg);
			
	    	throw new UsernameNotFoundException(ErrMsg); 
		} 
		
		Userapp userapp = ur.findByUsername(username);
		
		if (userapp == null)
		{
			ErrMsg = String.format("Utente %s non Trovato!!", username);
			
			log.warning(ErrMsg);
			
			throw new UsernameNotFoundException(ErrMsg);
		}
		
		log.info("Utente autenticato con successo!! - JwtAuthModule");
		
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
	
}
	