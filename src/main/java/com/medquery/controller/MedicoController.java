package com.medquery.controller;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dao.AgendaDao;
import com.medquery.dao.EspecializacaoDao;
import com.medquery.dao.MedicoDao;
import com.medquery.dao.PacienteDao;
import com.medquery.dto.DaoDto;
import com.medquery.dto.ValidatorDto;
import com.medquery.model.Medico;
import com.medquery.validator.MedicoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;

@Controller
public class MedicoController {

    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private EspecializacaoDao especializacaoDao;

    @Autowired
    private PacienteDao pacienteDao;

    @Autowired
    private AgendaDao agendaDao;

    @GetMapping(value = "/Admin/Painel")
    public ModelAndView home(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin(session);
        }
        ModelAndView pagina = new ModelAndView("admin");
        pagina.addObject("totalMedico", medicoDao.count());
        pagina.addObject("totalEspecializacao", especializacaoDao.count());
        pagina.addObject("totalPaciente", pacienteDao.count());
        pagina.addObject("totalConsultas", agendaDao.countAgendaByMedico((Medico)session.getAttribute("medico")));
        return pagina;
    }

    @GetMapping(value = "/Admin")
    public ModelAndView adminLogin(HttpSession session){
        Medico lMedico = (Medico) session.getAttribute("medico");
        if(lMedico != null){
            return home(session);
        }
        ModelAndView pagina = new ModelAndView("admin-login");
        pagina.addObject("msg", "Área exclusiva para adminsitradores");
        return pagina;
    }

    @PostMapping(value = "/Admin/Entrar")
    public ModelAndView adminEntrar(String usr, String pwd, HttpSession session){
        ModelAndView pagina = null;
        if(usr == null || usr.isEmpty()){
            pagina = new ModelAndView("admin-login");
            pagina.addObject("msg", "Esqueceu o nome de usuário né ??");
            return pagina;
        }

        if(pwd == null || pwd.isEmpty()){
            pagina = new ModelAndView("admin-login");
            pagina.addObject("msg", "Esqueceu a senha né ??");
            return pagina;
        }

        try{
            //region HASH DA SENHA
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(pwd.getBytes(), 0, pwd.length());
            pwd = new BigInteger(1,hash.digest()).toString(16);
            //endregion

            Medico medico = medicoDao.findByUsrAndPwd(usr, pwd);
            if(medico != null){
                pagina = new ModelAndView("admin");
                pagina.addObject("medico", medico);
                session.setAttribute("medico", medico);
                session.setAttribute("estaLogado", "SIM");
                return home(session);
            }else{
                pagina = new ModelAndView("admin-login");
                pagina.addObject("msg", "Ixiii, não achei vc :(");
                return pagina;
            }

        }catch (Exception e){
            pagina = new ModelAndView("admin-login");
            pagina.addObject("msg", "Esqueceu a senha né ??");
            return pagina;
        }


    }

    @GetMapping(value = "/Medico")
    public ModelAndView lista(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin(session);
        }
        ModelAndView pagina = new ModelAndView("medico/lista");
        pagina.addObject("items", medicoDao.findAll());
        return pagina;
    }

    @GetMapping(value = "/Medico/Novo")
    public ModelAndView novo(HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin(session);
        }
        ModelAndView pagina = new ModelAndView("medico/novo");
        pagina.addObject("medico", new Medico());
        pagina.addObject("especializacao", especializacaoDao.findAll());
        return pagina;
    }

    @PostMapping(value = "/Medico/Novo")
    public ResponseEntity novo(Medico medico, HttpSession session){

        ValidatorDto validacao = MedicoValidator.cadastroValidator(medico);
        if(!validacao.getOk()){
            DaoDto<Medico> resultado = new DaoDto<>(false, validacao.getMsg());
            return new ResponseEntity(resultado, HttpStatus.OK);
        }
        try{

            //region VERIFICA SE JAH EXISTE
            Medico jaExiste = null;

            jaExiste = medicoDao.findByCrm(medico.getCrm());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Já existe um médico com este CRM !");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }

            jaExiste = medicoDao.findByCpf(medico.getCpf());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Já existe um médico com este CPF !");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }

            jaExiste = medicoDao.findByEmail(medico.getEmail());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Acho que vc esqueceu que já cadastrou este médico né ?");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }

            jaExiste = medicoDao.findByTelefoneCelular(medico.getTelefoneCelular());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Esse numero já ta cadastrado aqui :( não foi vc ?");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }
            //endregion

            //region HASH DA SENHA
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(medico.getPwd().getBytes(), 0, medico.getPwd().length());
            medico.setPwd(new BigInteger(1,hash.digest()).toString(16));
            //endregion
            medicoDao.save(medico);
            DaoDto<Medico> res = new DaoDto<>(true, "Medico gravado com sucesso!");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            DaoDto<Medico> res = new DaoDto<>(false, "Erro!");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Medico/Editar/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin(session);
        }

        session.setAttribute("medicoCodigo", codigo);

        try{
            Medico medico = medicoDao.findOne(codigo);
            ModelAndView pagina = new ModelAndView("medico/editar");
            pagina.addObject("medico", medico);
            pagina.addObject("especializacao", especializacaoDao.findAll());
            return pagina;
        }catch (Exception e){
            return lista(session);
        }
    }

    @PostMapping(value = "/Medico/Editar")
    public ResponseEntity editarSave(Medico medico, HttpSession session){
//        if(session.getAttribute("medico") == null){
//            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
//            return erro(res.getMsg());
//        }
        medico.setCodigo((Long) session.getAttribute("medicoCodigo"));
        try{
            Medico sessionMedico = (Medico) session.getAttribute("medico");
            medico.setPwd(sessionMedico.getPwd());
            medicoDao.save(medico);
            DaoDto<Medico> res = new DaoDto<>(true, "Medico atualizado com sucesso!");
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception e){
            DaoDto<Medico> res = new DaoDto<>(false, "Erro!");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/Medico/Remover/{codigo}")
    public ModelAndView remover(@PathVariable("codigo") Long codigo, HttpSession session){
        if(session.getAttribute("medico") == null){
            DaoDto<Medico> res = new DaoDto<>(false, "Você precisa entrar no sistema!");
            return adminLogin(session);
        }

        try{
            medicoDao.delete(codigo);
            return lista(session);
        }catch (Exception e){
            e.printStackTrace();
            return lista(session);
        }
    }
}
