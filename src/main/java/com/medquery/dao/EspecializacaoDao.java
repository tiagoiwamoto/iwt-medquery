package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Especializacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspecializacaoDao extends JpaRepository<Especializacao, Long>{

    List<Especializacao> findAllByPalavraChaveContaining(String especializacao);

}