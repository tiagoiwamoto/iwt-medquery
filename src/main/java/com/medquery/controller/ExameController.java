package com.medquery.controller;

import com.medquery.dao.AgendaExameDao;
import com.medquery.dao.ExameDao;
import com.medquery.dto.DaoDto;
import com.medquery.model.AgendaExame;
import com.medquery.model.Exame;
import com.medquery.model.Medico;
import com.medquery.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 13/05/2018 - 08:46
 */
@Controller
@SuppressWarnings("all")
public class ExameController {

    @Autowired
    private ExameDao exameDao;

    @Autowired
    private AgendaExameDao agendaExameDao;

    @GetMapping(value = "/Exame/Gerenciar")
    private ModelAndView showAgendaExame(HttpSession session){
        ModelAndView pagina = new ModelAndView("exame/gerenciar");
        pagina.addObject("exames", agendaExameDao.findAll());
        return pagina;
    }

    @PostMapping(value = "/Exame/Agendar")
    private ResponseEntity agendarExame(AgendaExame agendaExame, HttpSession session){
        if(session.getAttribute("paciente") == null){
            DaoDto<Exame> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return new ResponseEntity(res, HttpStatus.OK);
        }
        Paciente sessionPaciente = (Paciente) session.getAttribute("paciente");
        agendaExame.setPaciente(sessionPaciente);
        agendaExame.setStatus(0);
        try{
            agendaExameDao.save(agendaExame);
            DaoDto<AgendaExame> res = new DaoDto<>(true, "Exame agendado com sucesso !");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            DaoDto<AgendaExame> res = new DaoDto<>(false, "Falha ao realizar agendamento !");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Exame/Todos")
    public ResponseEntity listaExames(){
        return new ResponseEntity(exameDao.findAll(), HttpStatus.OK);
    }

    public ModelAndView adminLogin(){
        ModelAndView pagina = new ModelAndView("admin-login");
        pagina.addObject("msg", "Área exclusiva para adminsitradores");
        return pagina;
    }

    @GetMapping(value = "/Exame/Lista")
    public ModelAndView home(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Exame> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }

        ModelAndView pagina = new ModelAndView("exame/lista");
        pagina.addObject("items", exameDao.findAll());
        return pagina;
    }

    @GetMapping(value = "/Exame/Novo")
    public ModelAndView novo(){
        ModelAndView pagina = new ModelAndView("exame/novo");
        return pagina;
    }

    @PostMapping(value = "/Exame")
    public ResponseEntity salvar(Exame exame, HttpSession session){
        try{
            exameDao.save(exame);
            DaoDto<Exame> res = new DaoDto<>(true, "Exame adicionado com sucesso!");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            DaoDto<Exame> res = new DaoDto<>(false, "Falha ao gravar este exame.");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Exame/Editar/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Exame> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        Exame exame = exameDao.findOne(codigo);
        ModelAndView pagina = new ModelAndView("exame/editar");
        pagina.addObject("exame", exame);

        return pagina;
    }

    @PostMapping(value = "/Exame/Editar")
    public ResponseEntity editar(Exame especializacao, HttpSession session){
        DaoDto<Exame> res = null;

        try{
            exameDao.save(especializacao);
            res = new DaoDto<>(true, "Yeee, está feito");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            res = new DaoDto<>(false, "Falha ao atualizar exame");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Exame/Remover/{codigo}")
    public ModelAndView remover(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Exame> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin();
        }
        try {
            exameDao.delete(codigo);
            return home(session);
        }catch (Exception e){
            return home(session);
        }
    }

    @GetMapping(value = "/Exame/Confirmar/{codigo}")
    public ModelAndView confirmar(@PathVariable(name = "codigo") Long codigo,
                                  HttpSession session){
        AgendaExame agenda = agendaExameDao.findOne(codigo);
        if(agenda.getStatus() != null){
            if(agenda.getStatus() == 1){
                DaoDto<AgendaExame> res = new DaoDto<>(false, "Este exame já foi cancelado :[");
                return showAgendaExame(session);
            }
        }
        if(agenda.getStatus() != null){
            if(agenda.getStatus() == 1){
                DaoDto<AgendaExame> res = new DaoDto<>(false, "Está consulta já foi confirmada :]");
                return showAgendaExame(session);
            }
        }

        try {
            agenda.setStatus(1);
            agenda.setCodigo(codigo);
            agendaExameDao.save(agenda);

            DaoDto<AgendaExame> res = new DaoDto<>(true, "Exame confirmado, um email será enviado ao paciente !");
            return showAgendaExame(session);
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<AgendaExame> res = new DaoDto<>(false, "Não consegui confirmar este exame, tente novamente :]");
            return showAgendaExame(session);
        }
    }

    @GetMapping(value = "/Exame/Cancelar/{codigo}")
    public ModelAndView cancelar(@PathVariable(name = "codigo") Long codigo,
                                 HttpSession session){
        AgendaExame agenda = agendaExameDao.findOne(codigo);
        if(agenda.getStatus() != null){
            if(agenda.getStatus() == 2){
                DaoDto<AgendaExame> res = new DaoDto<>(false, "Este exame já foi cancelado :[");
                ModelAndView pagina = new ModelAndView("exame/gerenciar");
                Medico medico = (Medico) session.getAttribute("medico");
                List<AgendaExame> mAgenda = agendaExameDao.findAll();
                pagina.addObject("exames", mAgenda);
                pagina.addObject("msgAgenda", res.getMsg());
                return pagina;
            }
        }
        if(agenda.getStatus() != null){
            if(agenda.getStatus() == 1){
                DaoDto<AgendaExame> res = new DaoDto<>(false, "Está consulta já foi confirmada :]");
                ModelAndView pagina = new ModelAndView("exame/gerenciar");
                Medico medico = (Medico) session.getAttribute("medico");
                List<AgendaExame> mAgenda = agendaExameDao.findAll();
                pagina.addObject("exames", mAgenda);
                pagina.addObject("msgAgenda", res.getMsg());
                return pagina;
            }
        }
        try {
            agenda.setStatus(2);
            agenda.setCodigo(codigo);
            agendaExameDao.save(agenda);
            DaoDto<AgendaExame> res = new DaoDto<>(true, "Exame cancelado, um email será enviado ao paciente !");
            ModelAndView pagina = new ModelAndView("exame/gerenciar");
            Medico medico = (Medico) session.getAttribute("medico");
            pagina.addObject("exames", agendaExameDao.findAll());
            pagina.addObject("msgAgenda", res.getMsg());
            return pagina;
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<AgendaExame> res = new DaoDto<>(false, "Não consegui cancelar este exame, tente novamente :]");
            ModelAndView pagina = new ModelAndView("exame/gerenciar");
            Medico medico = (Medico) session.getAttribute("medico");
            pagina.addObject("exames", agendaExameDao.findAll());
            pagina.addObject("msgAgenda", res.getMsg());
            return pagina;
        }
    }


}
