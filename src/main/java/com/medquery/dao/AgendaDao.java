package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Agenda;
import com.medquery.model.Medico;
import com.medquery.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AgendaDao extends JpaRepository<Agenda, Long> {

    Agenda findByDiaConsultaAndHoraConsultaAndMedico(Date diaConsulta, Date horaConsulta, Medico medico);
    List<Agenda> findAllByMedicoOrderByDiaConsultaDesc(Medico medico);
    Integer countAgendaByMedico(Medico medico);
    List<Agenda> findAllByPacienteAndConfirmadoEqualsOrderByCodigoDesc(Paciente paciente, Integer confirmado);

}
