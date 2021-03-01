package com.medquery.dao;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoDao extends JpaRepository<Medico, Long>{

    Medico findByCrm(String crm);
    Medico findByCpf(String cpf);
    Medico findByEmail(String email);
    Medico findByTelefoneCelular(Long telefone);
    Medico findByUsrAndPwd(String usr, String pwd);

}
