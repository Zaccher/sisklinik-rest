package com.sisklinik.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.SneakyThrows;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	
	private static String REALM = "REAME";
	
	@Autowired
	@Qualifier("UserappUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Bean
    BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
	
	// Per ora facciamo metchare tutto a entrambi i ruoli - 
	// più avanti vedremo di distinguere un po' le cose
	private static final String[] USER_MATCHER = {"/api/**"};
	private static final String[] ADMIN_MATCHER = {"/api/**"};
	
	@Bean
	@SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity http) 
	{
		http
			.csrf(csrf -> csrf.disable()) //disattiviamo il csrf - nella nostra app non servirà
			.cors(cors -> cors.configurationSource(corsConfigurationSource())) // configurazione del cors - Molto importante!
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configurazione del sessionManagement a STATELESS
			.httpBasic(e -> e.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())) // configurazione dell'authentication entry point dell'applicazione
			.authorizeHttpRequests(authz -> 
            {
				authz
				    .requestMatchers(ADMIN_MATCHER).hasRole("ADMIN")
				    .requestMatchers(USER_MATCHER).hasRole("USER")
				    .anyRequest().authenticated();
			}); // Con questo specifichiamo i matcher tra i ruoli e gli endpoint
		
		return http.build();
	}
	
	@Bean
	AuthEntryPoint getBasicAuthEntryPoint()
	{
		return new AuthEntryPoint();
	}
	
	@Autowired
	@SneakyThrows
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
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
