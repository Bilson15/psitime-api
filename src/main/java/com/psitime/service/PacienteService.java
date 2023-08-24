package com.psitime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.psitime.configs.TokenService;
import com.psitime.dto.AuthenticationDTO;
import com.psitime.dto.LoginResponseDTO;
import com.psitime.dto.PacienteDTO;
import com.psitime.dto.PacienteRegisterDTO;
import com.psitime.model.Paciente;
import com.psitime.repository.PacienteRepository;

@Service
public class PacienteService {
	
	@Autowired
	PacienteRepository repository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	public ResponseEntity<LoginResponseDTO> login(AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generetedToken((Paciente) auth.getPrincipal());
		//((Paciente) auth.getPrincipal()).get
		return ResponseEntity.ok(new LoginResponseDTO(((Paciente) auth.getPrincipal()).getId(), token));
	}
	
	
	public ResponseEntity<?> register(PacienteRegisterDTO data) {
		if(this.repository.findByCpf(data.cpf()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		
		Paciente newPaciente = new Paciente(data.nome(), data.email(), encryptedPassword, data.cpf(), data.dataNascimento());
		
		this.repository.save(newPaciente);
		
		return ResponseEntity.ok().build();
	}
	
	
	public ResponseEntity<PacienteDTO> getPacienteById(Long id) {
		if(!repository.existsById(id)) return ResponseEntity.notFound().build();
    	
	    	Paciente paciente = repository.getReferenceById(id);
	    	
	    	return ResponseEntity.ok(new PacienteDTO(paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getDataNascimento()));
	}
	    	
	

}
