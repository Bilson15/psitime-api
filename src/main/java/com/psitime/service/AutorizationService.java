package com.psitime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.psitime.repository.PacienteRepository;

@Service
public class AutorizationService implements UserDetailsService {
	
	@Autowired
	PacienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		return repository.findByCpf(cpf);
	}

}
