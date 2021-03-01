package com.medquery.validator;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dto.ValidatorDto;
import com.medquery.model.Especializacao;

public class EspecializacaoValidator {

    public static ValidatorDto cadastroValidator(Especializacao especializacao){
        ValidatorDto resultado = null;

        if(especializacao.getNome() == null || especializacao.getNome().isEmpty()){
            resultado = new ValidatorDto(false, "Especialização sem nome não rola né :p");
            return resultado;
        }

        if(especializacao.getNome().length() < 4){
            resultado = new ValidatorDto(false, "Acho que não existe uma especialização tão curta assim em LOL");
            return resultado;
        }

        if(especializacao.getPalavraChave().length() < 1){
            resultado = new ValidatorDto(false, "Nenhum palavra chave mesmo ?? que preguiça em :/");
            return resultado;
        }

        if(especializacao.getDescricao().isEmpty()){
            resultado = new ValidatorDto(false, "Descreva um pouco pelo menos.");
            return resultado;
        }

        resultado = new ValidatorDto(true, "Tudo certo!");
        return resultado;
    }
}
