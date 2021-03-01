package com.medquery.controller;

import com.medquery.dao.VisitanteAcessoDao;
import com.medquery.model.VisitanteAcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 09/04/2018 - 21:16
 */
@SuppressWarnings("all")
@Controller
public class HelloController {

    @Autowired
    private VisitanteAcessoDao visitanteAcessoDao;

    @GetMapping(value = "/Hello")
    public ModelAndView hello(HttpSession session, HttpServletRequest request){
        //region PAGINA ACESSADA
        if(session.getAttribute("visitante") == null){
            UUID visitante = null;
            VisitanteAcesso nVisitante = new VisitanteAcesso();
            nVisitante.setNome(visitante.randomUUID().toString());
            nVisitante.setPagina("Home");
            nVisitante.setHoraAcesso(new Date());
            nVisitante.setOrigem(request.getRemoteAddr());
            session.setAttribute("visitante", visitante.randomUUID().toString());
            session.setAttribute("origem", request.getRemoteAddr());
            session.setAttribute("session", true);
            visitanteAcessoDao.save(nVisitante);
        }else{
            VisitanteAcesso nVisitante = new VisitanteAcesso();
            nVisitante.setNome(session.getAttribute("visitante").toString());
            nVisitante.setPagina("Home");
            nVisitante.setHoraAcesso(new Date());
            nVisitante.setOrigem(request.getRemoteAddr());
            visitanteAcessoDao.save(nVisitante);
        }
        //endregion

        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                ModelAndView pagina = new ModelAndView("home");
                pagina.addObject("estaLogado", "SIM");
                return pagina;
            }
        }

        ModelAndView pagina = new ModelAndView("home");
        pagina.addObject("estaLogado", "NAO");
        return pagina;
    }



}
