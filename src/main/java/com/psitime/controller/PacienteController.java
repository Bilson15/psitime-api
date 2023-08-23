package com.psitime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psitime.configs.TokenService;
import com.psitime.dto.AuthenticationDTO;
import com.psitime.dto.PacienteDTO;
import com.psitime.dto.LoginResponseDTO;
import com.psitime.model.Paciente;
import com.psitime.repository.PacienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/paciente")
public class PacienteController {
	@Autowired
	private PacienteRepository repository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generetedToken((Paciente) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid PacienteDTO data) {
		if(this.repository.findByCpf(data.cpf()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		
		Paciente newPaciente = new Paciente(data.nome(), data.email(), encryptedPassword, data.cpf(), data.dataNascimento());
		
		this.repository.save(newPaciente);
		
		return ResponseEntity.ok().build();
	}

}
