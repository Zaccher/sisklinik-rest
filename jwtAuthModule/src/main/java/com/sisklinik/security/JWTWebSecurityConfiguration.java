package com.sisklinik.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.SneakyThrows;
 
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTWebSecurityConfiguration {

	@Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	@Qualifier("JwtAuthUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/* Questo provider specifica il servizio di dettagli utente e il codificatore di password
	 * per l'autenticazione.*/
	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	@SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests((authorize) -> authorize
	                .requestMatchers("/auth").permitAll()
	                .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //indica che il modulo sarà senza stato e le sessioni non sono utilizzate per il tracciamento degli utenti
	        .authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Questo filtro è responsabile dell'elaborazione dei token JWT e dell'autenticazione degli utenti.
	        .build();

	}	
	
}
