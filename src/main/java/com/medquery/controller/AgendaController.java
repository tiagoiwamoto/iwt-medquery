package com.medquery.controller;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dao.AgendaDao;
import com.medquery.dao.AtendimentoDao;
import com.medquery.dao.ConsultaDao;
import com.medquery.dao.MedicoDao;
import com.medquery.dto.DaoDto;
import com.medquery.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class AgendaController {

    @Autowired
    private AgendaDao agendaDao;

    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private AtendimentoDao atendimentoDao;

    @Autowired
    private ConsultaDao exameDao;

    @PostMapping(value = "/Agenda/Gravar")
    public ResponseEntity gravarAgenda(@RequestParam(name = "medico") String medicoCodigo,
                                       @RequestParam(name = "horaConsulta") String horaConsulta,
                                       @RequestParam(name = "diaConsulta") String diaConsulta,
                                       HttpSession session){
        //TODO:Validator
        if(session.getAttribute("paciente") == null){
            DaoDto<Agenda> res = new DaoDto<>(false, "Você precisa entrar no sistema para fazer agendamentos :)");
            return new ResponseEntity(res, HttpStatus.OK);
        }

        try{
            Agenda agenda = new Agenda();
            Medico medico = medicoDao.findOne(Long.valueOf(medicoCodigo));
            agenda.setMedico(medico);
            agenda.setPaciente((Paciente)session.getAttribute("paciente"));
            agenda.setDiaConsulta(new Date(diaConsulta));
            agenda.setHoraConsulta(new Date(diaConsulta + " " + horaConsulta));
            Agenda agendaExiste = agendaDao.findByDiaConsultaAndHoraConsultaAndMedico(agenda.getDiaConsulta(), agenda.getHoraConsulta(), medico);
            if(agendaExiste != null){
                DaoDto<Agenda> res = new DaoDto<>(false, "Xiii, este horario já esta reservado :[");
                return new ResponseEntity(res, HttpStatus.OK);
            }
            agendaDao.save(agenda);
            DaoDto<Agenda> res = new DaoDto<>(true, "Esta agendado :]");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<Agenda> res = new DaoDto<>(false, "Deu caca :[ " + e.getMessage());
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Agenda/Medico")
    public ModelAndView agendaMedico(HttpSession session){
        Medico medico = (Medico) session.getAttribute("medico");
        List<Agenda> agenda = agendaDao.findAllByMedicoOrderByDiaConsultaDesc(medico);
        ModelAndView pagina = new ModelAndView("agenda/gerenciar");
        pagina.addObject("agenda", agenda);
        return pagina;
    }

    @GetMapping(value = "/Agenda/Confirmar/{codigo}")
    public ModelAndView confirmar(@PathVariable(name = "codigo") Long codigo,
                                   HttpSession session){
        Agenda agenda = agendaDao.findOne(codigo);
        if(agenda.getCancelado() != null){
            if(agenda.getCancelado() == 1){
                DaoDto<Agenda> res = new DaoDto<>(false, "Está consulta já foi cancelada :[");
                return agendaMedico(session);
            }
        }
        if(agenda.getConfirmado() != null){
            if(agenda.getConfirmado() == 1){
                DaoDto<Agenda> res = new DaoDto<>(false, "Está consulta já foi confirmada :]");
                return agendaMedico(session);
            }
        }

        try {
            agenda.setConfirmado(1);
            agendaDao.save(agenda);
            Consulta exame = new Consulta();
            exame.setPaciente(agenda.getPaciente());
            exame.setMedico(agenda.getMedico());
            exame.setDescricao("Atendimento iniciado.");
            exameDao.save(exame);
            Atendimento nAtendimento = new Atendimento();
            nAtendimento.setMedico(agenda.getMedico());
            nAtendimento.setPaciente(agenda.getPaciente());
            DaoDto<Agenda> res = new DaoDto<>(true, "Consulta confirmada, um email será enviado ao paciente !");
            return agendaMedico(session);
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<Agenda> res = new DaoDto<>(false, "Não consegui confirmar esta consulta, tente novamente :]");
            return agendaMedico(session);
        }
    }

    @GetMapping(value = "/Agenda/Cancelar/{codigo}")
    public ModelAndView cancelar(@PathVariable(name = "codigo") Long codigo,
                                   HttpSession session){
        Agenda agenda = agendaDao.findOne(codigo);
        if(agenda.getCancelado() != null){
            if(agenda.getCancelado() == 1){
                DaoDto<Agenda> res = new DaoDto<>(false, "Está consulta já foi cancelada :[");
                ModelAndView pagina = new ModelAndView("agenda/gerenciar");
                Medico medico = (Medico) session.getAttribute("medico");
                List<Agenda> mAgenda = agendaDao.findAllByMedicoOrderByDiaConsultaDesc(medico);
                pagina.addObject("agenda", mAgenda);
                pagina.addObject("msgAgenda", res.getMsg());
                return pagina;
            }
        }
        if(agenda.getConfirmado() != null){
            if(agenda.getConfirmado() == 1){
                DaoDto<Agenda> res = new DaoDto<>(false, "Está consulta já foi confirmada :]");
                ModelAndView pagina = new ModelAndView("agenda/gerenciar");
                Medico medico = (Medico) session.getAttribute("medico");
                List<Agenda> mAgenda = agendaDao.findAllByMedicoOrderByDiaConsultaDesc(medico);
                pagina.addObject("agenda", mAgenda);
                pagina.addObject("msgAgenda", res.getMsg());
                return pagina;
            }
        }
        try {
            agenda.setCancelado(1);
            agendaDao.save(agenda);
            DaoDto<Agenda> res = new DaoDto<>(true, "Consulta cancelada, um email será enviado ao paciente !");
            ModelAndView pagina = new ModelAndView("agenda/gerenciar");
            Medico medico = (Medico) session.getAttribute("medico");
            List<Agenda> mAgenda = agendaDao.findAllByMedicoOrderByDiaConsultaDesc(medico);
            pagina.addObject("agenda", mAgenda);
            pagina.addObject("msgAgenda", res.getMsg());
            return pagina;
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<Agenda> res = new DaoDto<>(false, "Não consegui cancelar esta consulta, tente novamente :]");
            ModelAndView pagina = new ModelAndView("agenda/gerenciar");
            Medico medico = (Medico) session.getAttribute("medico");
            List<Agenda> mAgenda = agendaDao.findAllByMedicoOrderByDiaConsultaDesc(medico);
            pagina.addObject("agenda", mAgenda);
            pagina.addObject("msgAgenda", res.getMsg());
            return pagina;
        }
    }

}
