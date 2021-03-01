package com.medquery.controller;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dao.EspecializacaoDao;
import com.medquery.dto.DaoDto;
import com.medquery.dto.ValidatorDto;
import com.medquery.model.Especializacao;
import com.medquery.validator.EspecializacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class EspecializacaoController {

    @Autowired
    private EspecializacaoDao especializacaoDao;

    public ModelAndView adminLogin(){
        ModelAndView pagina = new ModelAndView("admin-login");
        pagina.addObject("msg", "Área exclusiva para adminsitradores");
        return pagina;
    }

    @GetMapping(value = "/Especializacao")
    public ModelAndView lista(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Especializacao> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        ModelAndView pagina = new ModelAndView("especializacao/lista");
        pagina.addObject("items", especializacaoDao.findAll());
        return pagina;
    }

    @GetMapping(value = "/Especializacao/Nova")
    public ModelAndView nova(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Especializacao> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        ModelAndView pagina = new ModelAndView("especializacao/novo");
        return pagina;
    }

    @PostMapping(value = "/Especializacao/Nova")
    public ResponseEntity gravar(Especializacao especializacao, HttpSession session){
        ValidatorDto val = EspecializacaoValidator.cadastroValidator(especializacao);
        if(!val.getOk()){
            return new ResponseEntity(val, HttpStatus.OK);
        }
        try{
            especializacao.setUsuarioCriacao("Tiago");
            especializacao.setPalavraChave(especializacao.getPalavraChave().substring(0, especializacao.getPalavraChave().length() - 1));
            especializacaoDao.save(especializacao);
            DaoDto<Especializacao> resultado = new DaoDto<>(true, "Ta feito ;)");
            return new ResponseEntity(resultado, HttpStatus.OK);
        }catch (Exception e){
            DaoDto<Especializacao> resultado = new DaoDto<>(false, "Não consegui gravar :(");
            return new ResponseEntity(resultado, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Especializacao/Editar/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Especializacao> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        Especializacao especializacao = especializacaoDao.findOne(codigo);
        String[] split = especializacao.getPalavraChave().split(",");
        ModelAndView pagina = new ModelAndView("especializacao/editar");
        pagina.addObject("palavras", split);
        pagina.addObject("especializacao", especializacao);
        session.setAttribute("palavras", especializacao);

        return pagina;
    }

    @PostMapping(value = "/Especializacao/Editar")
    public ResponseEntity editar(Especializacao especializacao, HttpSession session){
        DaoDto<Especializacao> res = null;

        try{
            Especializacao sEspecializacao = (Especializacao) session.getAttribute("palavras");
            especializacao.setPalavraChave(sEspecializacao.getPalavraChave());
            especializacaoDao.save(especializacao);
            res = new DaoDto<>(true, "Yeee, está feito", especializacao);
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            res = new DaoDto<>(false, "Falha ao atualizar especialização");
            return new ResponseEntity(res, HttpStatus.OK);
        }

    }

    @GetMapping(value = "/Especializacao/Remover/{codigo}")
    public ModelAndView remover(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Especializacao> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        try {
            especializacaoDao.delete(codigo);
            return lista(session);
        }catch (Exception e){
            return lista(session);
        }
    }




}
