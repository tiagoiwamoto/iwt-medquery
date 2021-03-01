package com.medquery.validator;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dto.ValidatorDto;
import com.medquery.model.Medico;

import java.util.Date;

public class MedicoValidator {

    public static ValidatorDto cadastroValidator(Medico medico){

        ValidatorDto resultado = null;

        if(medico.getCrm() == null || medico.getCrm().isEmpty()){
            resultado = new ValidatorDto(false, "Médico sem CRM ??");
            return resultado;
        }

        //TODO: Validacoa de CRM

        if(medico.getCpf() == null || medico.getCpf().isEmpty()){
            resultado = new ValidatorDto(false, "Então essa pessoa não tem CPF ?? LOL");
            return resultado;
        }

        //TODO: Validacao de CPF

        if(medico.getDataNascimento() == null){
            resultado = new ValidatorDto(false, "Ele não nasceu ainda ? OMG");
            return resultado;
        }

        if(new Date().getTime() - medico.getDataNascimento().getTime() < 568030638393L){
            resultado = new ValidatorDto(false, "Com essa idade acho que ainda não é um médico em ;)");
            return resultado;
        }

        if(medico.getTelefoneCelular() == null){
            resultado = new ValidatorDto(false, "Não ter um telefone residencial eu entendo, mas sem celular ?");
            return resultado;
        }

        if(medico.getNomeCompleto() == null || medico.getNomeCompleto().isEmpty()){
            resultado = new ValidatorDto(false, "Esqueceu o nome né ??");
            return resultado;
        }

        if(medico.getNomeCompleto().length() < 5){
            resultado = new ValidatorDto(false, "Tem que ser o nome completo né ;)");
            return resultado;
        }

        if(medico.getEmail() == null || medico.getEmail().isEmpty()){
            resultado = new ValidatorDto(false, "Esqueceu o email né ?");
            return resultado;
        }

        if(medico.getUsr() == null || medico.getUsr().isEmpty()){
            resultado = new ValidatorDto(false, "Informe um nome de acesso ;)");
            return resultado;
        }

        if(medico.getUsr().length() < 5){
            resultado = new ValidatorDto(false, "Geralmente se usa o nome.sobrenome");
            return resultado;
        }

        if(medico.getPwd() == null || medico.getPwd().isEmpty()){
            resultado = new ValidatorDto(false, "Sem senha não tem como né rsrs");
            return resultado;
        }

        if(medico.getPwd().length() < 6){
            resultado = new ValidatorDto(false, "Que senha mais sem vergonha essa em rsrs");
            return resultado;
        }

        resultado = new ValidatorDto(true, "Tudo certo");
        return resultado;

    }

}
