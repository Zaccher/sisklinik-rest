package com.sisklinik.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Component
@Log
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	@Qualifier("JwtAuthUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtConfig jwtConfig;

	/*  Il metodo doFilterInternal(), che viene invocato per ogni richiesta che viene fatta a questo modulo. */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.info(String.format("Authentication Request For '{%s}'", request.getRequestURL()));

		/* Il filtro inizia controllando se c'è un'intestazione "Authorization" nella richiesta e se contiene un token Bearer. */
		final String requestTokenHeader = request.getHeader(jwtConfig.getHeader());
		
		/* Le richieste che non sono correlate all'accesso utente in genere non hanno un token JWT nelle loro 
		 * intestazioni, quindi passano alla successiva catena di filtri senza alcuna elaborazione correlata 
		 * al token.
		 */
		if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		try {
			
			/* Se viene trovata l'intestazione "Authorization" e inizia con "Bearer", indicando la presenza di un token di accesso, 
			 * il filtro procede alla convalida e all'autenticazione di tale token.
			 */
			
			final String jwt = requestTokenHeader.substring(7); // Devo togliergli il Bearer 
            final String username = jwtTokenUtil.getUsernameFromToken(jwt);
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (username != null && authentication == null) {
            	
            	UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            	
            	/* Quindi, convalida questo token utilizzando il JwtTokenUtil fornito, 
            	 * assicurandosi che sia un token valido e non scaduto.
            	 */
            	if (jwtTokenUtil.validateToken(jwt, userDetails)) {
            		
            		/* Se il token è valido, il filtro autentica la richiesta creando un oggetto `Authentication`. 
            		 * Questo oggetto rappresenta lo stato di autenticazione dell'utente e contiene informazioni 
            		 * sull'utente, come il suo nome utente e le sue autorità.
            		 */
            		
            		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            		
            		authToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            		
            		/* L'utente autenticato viene quindi memorizzato nel `SecurityContext`, 
            		 * garantendogli l'accesso alle risorse protette nell'applicazione.
            		 */
            		
            		SecurityContextHolder.getContext().setAuthentication(authToken);
            		
            	}
            }
            
            filterChain.doFilter(request, response);
			
		}catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
		
	}

}
