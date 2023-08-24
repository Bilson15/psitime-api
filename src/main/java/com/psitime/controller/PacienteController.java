package com.psitime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psitime.dto.AuthenticationDTO;
import com.psitime.dto.PacienteRegisterDTO;
import com.psitime.dto.LoginResponseDTO;
import com.psitime.dto.PacienteDTO;
import com.psitime.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/paciente")
public class PacienteController {
	
	@Autowired
	PacienteService service;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		return this.service.login(data);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid PacienteRegisterDTO data) {
		return this.service.register(data);
	}
	
	@GetMapping("/{idPaciente}")
	public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable(value = "idPaciente") Long idPaciente){
		return this.service.getPacienteById(idPaciente);
	}

}
