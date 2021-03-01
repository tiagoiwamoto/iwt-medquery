package com.medquery.validator;

import com.medquery.dto.ValidatorDto;
import com.medquery.model.Paciente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:38
 */
public class PacienteValidator {

    public static ValidatorDto cadastroValidator(Paciente paciente){
        ValidatorDto resultado = null;

        //region NOME
        if(paciente.getNome() == null || paciente.getNome().isEmpty()){
            resultado = new ValidatorDto(false, "MEU DEEEEEUS vc não tem nome ????");
            return resultado;
        }
        if(paciente.getNome().length() < 4){
            resultado = new ValidatorDto(false, "Aaaa pare, será que existe um nome e sobrenome tão curtinho assim ??");
            return resultado;
        }
        if(paciente.getNome().length() > 100){
            resultado = new ValidatorDto(false, "MEU DEEEEEUS que baita nome em, eu não consigo lembrar então deixa um pouco menor blza ?");
            return resultado;
        }
        //endregion

        //region CPF
        if(paciente.getCpf() == null || paciente.getCpf().isEmpty()){
            resultado = new ValidatorDto(false, "Então vc não tem CPF ? será que não conhece como CIC ??");
            return resultado;
        }

        if(paciente.getCpf().length() > 11){
            resultado = new ValidatorDto(false, "Que baita CPF esse seu em rsrsrs");
            return resultado;
        }

        //TODO: Validar cpf valido/invalido
        //endregion

        //region TELEFONE
        if(paciente.getTelefone() == null || paciente.getTelefone() == 0){
            resultado = new ValidatorDto(false, "Seu telefone quebrou ? pq eu não acredito que vc não tenha :p");
            return resultado;
        }
        //endregion

        //region EMAIL
        if(paciente.getEmail() == null || paciente.getEmail().isEmpty()){
            resultado = new ValidatorDto(false, "Então vc não tem EMAIL ? conta outra vai rsrsrs");
            return resultado;
        }
        //TODO: Email valido
        //endregion

        //region SENHA
        if(paciente.getSenha().length() < 4){
            resultado = new ValidatorDto(false, "HiHiHi sua senha ultra secreta ta facin facin");
            return resultado;
        }
        if(paciente.getSenha().length() < 6){
            resultado = new ValidatorDto(false, "Quer umas dicas pra criar senha ?? essa ta baba demais...");
            return resultado;
        }
        //endregion


        resultado = new ValidatorDto(true, "Tudo certo!");
        return resultado;
    }

    public static ValidatorDto loginValidator(String login, String senha){
        ValidatorDto resultado = null;

        //region LOGIN
        if(login == null || login.isEmpty()){
            resultado = new ValidatorDto(false, "Para vc entrar precisa nos dizer o seu email :)");
            return resultado;
        }

        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(login);
        if(!matcher.find()){
            resultado = new ValidatorDto(false, "Vc sabe que o email é parecido com isso né? nome@servidor.com");
            return resultado;
        }

        if(senha == null || senha.isEmpty()){
            resultado = new ValidatorDto(false, "Se vc não falar a senha fica difícil né :p");
            return resultado;
        }
        //endregion
        resultado = new ValidatorDto(true, "Tudo certo!");
        return resultado;
    }
}
