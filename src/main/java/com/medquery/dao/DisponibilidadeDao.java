package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Disponibilidade;
import com.medquery.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DisponibilidadeDao  extends JpaRepository<Disponibilidade, Long>{

    Disponibilidade findByDiaSemanaAndHoraDisponivelAndMedico(String diaSemana, Date horaDisponivel, Medico codigoMedico);

}
