package com.sisklinik.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .authorizeHttpRequests((authorize) -> authorize
	                .requestMatchers("/auth").permitAll()
	                .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //indica che il modulo sarà senza stato e le sessioni non sono utilizzate per il tracciamento degli utenti
	        .authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Questo filtro è responsabile dell'elaborazione dei token JWT e dell'autenticazione degli utenti.
	        .build();

	}	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		// Lista degli header autorizzati
		List<String> allowedHeaders = new ArrayList<String>();
		allowedHeaders.add("Authorization");
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("Accept");
		allowedHeaders.add("x-requested-with");
		allowedHeaders.add("Cache-Control");
	
	  
		// Gli indirizzi di riferimento autorizzatti (se ne potranno aggiungere anche altri)
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE","PUT")); // relativi method http autorizzati
		configuration.setMaxAge((long) 3600); // Periodo di validità
		configuration.setAllowedHeaders(allowedHeaders);
	  
		// Riferimento del cors applicato all'intero dominio della web api
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
	  
		return source;
	}
	
}
