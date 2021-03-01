package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Consulta;
import com.medquery.model.Medico;
import com.medquery.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaDao extends JpaRepository<Consulta, Long> {

    List<Consulta> findAllByMedico(Medico medico);
    List<Consulta> findAllByPaciente(Paciente paciente);
    Integer countAllByPaciente(Paciente paciente);

}
