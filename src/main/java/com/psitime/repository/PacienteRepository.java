package com.psitime.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.psitime.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	UserDetails findByCpf(String cpf);

}
