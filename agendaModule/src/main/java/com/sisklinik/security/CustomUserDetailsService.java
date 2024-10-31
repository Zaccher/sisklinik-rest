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
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserappRepository ur;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {

		String errMsg = "";
		
		if(username == null || username.length() < 5) {
			
			errMsg = "Username assente o non valido";
			log.warning(errMsg);
			throw new UsernameNotFoundException(errMsg);
		}
		
		Userapp userapp = ur.findByUsername(username);
		
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

}
