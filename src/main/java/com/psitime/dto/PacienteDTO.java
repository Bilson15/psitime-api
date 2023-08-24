package com.psitime.dto;

import java.sql.Date;

public record PacienteDTO(String nome, String email, String cpf, Date dataNascimento) {

}
