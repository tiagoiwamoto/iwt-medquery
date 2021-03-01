package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Atendimento;
import com.medquery.model.Consulta;
import com.medquery.model.Medico;
import com.medquery.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoDao extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findAllByMedicoAndPacienteAndConsulta(Medico medico, Paciente paciente, Consulta consulta);

    List<Atendimento> findAllByPacienteOrderByCodigoDesc(Paciente paciente);

    Integer countAllByPacienteAndQuemEnviou(Paciente paciente, Integer quemEnviou);

}
