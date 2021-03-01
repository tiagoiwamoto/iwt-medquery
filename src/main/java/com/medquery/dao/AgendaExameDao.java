package com.medquery.dao;

import com.medquery.model.AgendaExame;
import com.medquery.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 13/05/2018 - 16:57
 */
public interface AgendaExameDao extends JpaRepository<AgendaExame, Long>{

    List<AgendaExame> findAllByPacienteOrderByDataHoraDesc(Paciente paciente);
    Integer countAllByPaciente(Paciente paciente);

}
