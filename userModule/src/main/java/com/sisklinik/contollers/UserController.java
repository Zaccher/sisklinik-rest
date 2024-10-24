package com.sisklinik.contollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisklinik.dtos.UserappDto;
import com.sisklinik.exceptions.BindingException;
import com.sisklinik.exceptions.InternalServerErrorException;
import com.sisklinik.params.input.UserappParamsInput;
import com.sisklinik.params.output.UserappOutput;
import com.sisklinik.services.UserService;
import com.sisklinik.utility.UserUtility;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api")
public class UserController {
	
	@Autowired
	UserService us;
	
	@Autowired
	UserUtility userUtility;
	
	@SneakyThrows // questa annotation serve per il reminder delle eccezioni senza utilizzare altro nei metodi
	@GetMapping(value = "/getAllUsers", produces = "application/json")
	ResponseEntity<List<UserappDto>> getAllUsers() {
		
		List<UserappDto> listaResult = new ArrayList<>();
		
		try {
			
			listaResult = us.findAllUsers();
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - getAllUsers");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
			
		}
		
		return new ResponseEntity<List<UserappDto>>(listaResult, HttpStatus.OK);
		
	}
	
	@SneakyThrows
	@GetMapping(value = "/verifyUser", produces = "application/json")
	ResponseEntity<UserappOutput> verifyUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		UserappDto result = new UserappDto();
		
		UserappOutput output = new UserappOutput();
		
		try {
			
			result = us.verifyUserapp(username, password);
			
			if(result != null) {
				
				output.setUserappDto(result);
				output.setResult("Utente esistente!");
				
			}else {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Utenza non trovata! - verifyUserapp");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
				
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! - verifyUser");
			log.warning(errMsg);
			throw new InternalServerErrorException(errMsg);
		}
		
		return new ResponseEntity<UserappOutput>(output, HttpStatus.OK);
	}
	

	@SneakyThrows
	@PostMapping(value = "/user/insert" , produces = "application/json")
    @Transactional
    ResponseEntity<UserappOutput> insertUser(@Valid @RequestBody UserappParamsInput patientInput, BindingResult bindingResult) {
		
		UserappDto userappDto = null;
		UserappOutput userappOutput = new UserappOutput();
		
		//controllo validità dati
		if (bindingResult.hasErrors()) {
			
			String MsgErr = userUtility.SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
			
		}
		
		try {
			
			userappDto = us.insertNewUser(patientInput);
			
			if(userappDto == null) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Inserimento nuovo user non effettuato! - insertUser");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
			}else {
				
				userappOutput.setUserappDto(userappDto);
				userappOutput.setResult("User creato con successo!");
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
					+ "- Eccezione Interna! - insertUser");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
		}
		
		return null;
	}

	@SneakyThrows
	@PostMapping(value = "/user/update" , produces = "application/json")
    @Transactional
    ResponseEntity<UserappOutput> updateUser(@Valid @RequestBody UserappParamsInput patientInput, BindingResult bindingResult) {
		
		UserappDto userappDto = null;
		UserappOutput userappOutput = new UserappOutput();
		
		//controllo validità dati
		if (bindingResult.hasErrors()) {
			
			String MsgErr = userUtility.SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
			
		}
		
		try {
			
			userappDto = us.updateUser(patientInput);
			
			if(userappDto == null) {
				
				String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
						+ "- Aggiornamento user non effettuato! - updateUser");
				
				log.warning(errMsg);
				
				throw new InternalServerErrorException(errMsg);
			}else {
				
				userappOutput.setUserappDto(userappDto);
				userappOutput.setResult("User aggiornato con successo!");
			}
			
		}catch (Exception e) {
			
			String errMsg = String.format("Errore interno del server. Contattare l'assistenza! "
					+ "- Eccezione Interna! - updateUser");
			
			log.warning(errMsg);
			
			throw new InternalServerErrorException(errMsg);
		}
		
		return null;
	}


}
