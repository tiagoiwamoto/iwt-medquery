package com.medquery.controller;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dao.DisponibilidadeDao;
import com.medquery.dao.EspecializacaoDao;
import com.medquery.dao.MedicoDao;
import com.medquery.dto.DaoDto;
import com.medquery.model.Disponibilidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@SuppressWarnings("all")
@Controller
public class DisponibilidadeController {

    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private DisponibilidadeDao disponibilidadeDao;

    @Autowired
    private EspecializacaoDao especializacaoDao;

    public ModelAndView adminLogin(){
        ModelAndView pagina = new ModelAndView("admin-login");
        pagina.addObject("msg", "Área exclusiva para adminsitradores");
        return pagina;
    }

    @GetMapping(value = "/Disponibilidade")
    public ModelAndView agendaLista(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Disponibilidade> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        ModelAndView pagina = new ModelAndView("disponibilidade/lista");
        pagina.addObject("esp", especializacaoDao.findAll());


        return pagina;
    }

    @GetMapping(value = "/Disponibilidade/Nova")
    public ModelAndView nova(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Disponibilidade> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        ModelAndView pagina = new ModelAndView("disponibilidade/novo");
        pagina.addObject("medicos", medicoDao.findAll());
        return pagina;
    }

    @PostMapping(value = "/Disponibilidade/Nova")
    public ResponseEntity novaGravar(Disponibilidade disponibilidade){

        try{
            Disponibilidade existe = disponibilidadeDao.findByDiaSemanaAndHoraDisponivelAndMedico(disponibilidade.getDiaSemana(), disponibilidade.getHoraDisponivel(), disponibilidade.getMedico());
            if(existe != null){
                DaoDto<Disponibilidade> res = new DaoDto<>(false, "Opa, este médico já esta para este dia e hora.");
                return new ResponseEntity(res, HttpStatus.OK);
            }
            disponibilidadeDao.save(disponibilidade);
            DaoDto<Disponibilidade> res = new DaoDto<>(true, "Ficou gravadinho...");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<Disponibilidade> res = new DaoDto<>(false, "Deu ruim...");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }


}
