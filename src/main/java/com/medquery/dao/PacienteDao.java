package com.medquery.dao;

import com.medquery.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:38
 */
public interface PacienteDao extends JpaRepository<Paciente, Long> {

    Paciente findByCpf(String cpf);
    Paciente findByEmail(String email);
    Paciente findByTelefone(Long telefone);
    Paciente findByEmailAndSenha(String email, String senha);
}
