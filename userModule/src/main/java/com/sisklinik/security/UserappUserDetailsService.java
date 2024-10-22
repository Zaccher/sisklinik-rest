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
import org.springframework.transaction.annotation.Transactional;

import com.sisklinik.entities.Userapp;
import com.sisklinik.services.UserService;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@Service("UserappUserDetailsService")
public class UserappUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly=true)
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {

		String errMsg = "";
		
		if(username == null || username.length() < 5) {
			
			errMsg = "Username assente o non valido";
			log.warning(errMsg);
			throw new UsernameNotFoundException(errMsg);
		}
		
		Userapp userapp = userService.verifyUserapp(username);
		
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
		listaRuoli.add("USER");
		listaRuoli.add("ADMIN");
		
		
		String[] profili = listaRuoli
				 .stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		
		builder.authorities(profili); 
		
		return builder.build();
	}

}
