package com.psitime.dto;

import java.sql.Date;

public record PacienteRegisterDTO(String nome, String email, String senha, String cpf, Date dataNascimento) {

}
