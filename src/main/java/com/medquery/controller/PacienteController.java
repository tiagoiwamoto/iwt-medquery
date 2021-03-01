package com.medquery.controller;

import com.medquery.dao.*;
import com.medquery.dto.DaoDto;
import com.medquery.dto.ExceptionDto;
import com.medquery.dto.ValidatorDto;
import com.medquery.model.*;
import com.medquery.validator.PacienteValidator;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:53
 */
@SuppressWarnings("all")
@Controller
public class PacienteController {

    @Autowired
    private PacienteDao pacienteDao;
    @Autowired
    private VisitanteAcessoDao visitanteAcessoDao;
    @Autowired
    private ConsultaDao consultaDao;
    @Autowired
    private ExameDao exameDao;
    @Autowired
    private EspecializacaoDao especializacaoDao;
    @Autowired
    private AtendimentoDao atendimentoDao;
    @Autowired
    private AgendaExameDao agendaExameDao;
    @Autowired
    private AgendaDao agendaDao;

    @GetMapping(value = "/")
    public ModelAndView hello(HttpSession session){
        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                ModelAndView pagina = new ModelAndView("home");
                pagina.addObject("exames", exameDao.findAll());
                session.setAttribute("estaLogado", "SIM");
                return pagina;
            }
        }

        ModelAndView pagina = new ModelAndView("home");
        pagina.addObject("exames", exameDao.findAll());
        session.setAttribute("estaLogado", "NAO");

        return pagina;
    }

    @PostMapping(value = "/Busca")
    public ModelAndView buscaE(@RequestParam("especializacao") String especializacao){
        List<Especializacao> lista = especializacaoDao.findAllByPalavraChaveContaining(especializacao);
        ModelAndView pagina = new ModelAndView("user/resultado-consulta");
        pagina.addObject("esp", lista);
        return pagina;

    }


    @PostMapping(value = "/Paciente/Gravar")
    public ResponseEntity gravaPaciente(Paciente paciente){
        //region VALIDACOES
        ValidatorDto validacao = null;
        validacao = PacienteValidator.cadastroValidator(paciente);
        if(!validacao.getOk()){
            return new ResponseEntity(validacao, HttpStatus.OK);
        }
        //endregion

        try{
            //region VERIFICA SE JAH EXISTE
            Paciente jaExiste = null;
            jaExiste = pacienteDao.findByCpf(paciente.getCpf());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Acho que vc esqueceu a senha em, ou clonaram vc OMG!");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }

            jaExiste = pacienteDao.findByEmail(paciente.getEmail());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Acho que vc esqueceu que já tem cadastro aqui né ?");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }

            jaExiste = pacienteDao.findByTelefone(paciente.getTelefone());
            if(jaExiste != null){
                validacao = new ValidatorDto(false, "Esse numero já ta cadastrado aqui :( não foi vc ?");
                return new ResponseEntity(validacao, HttpStatus.OK);
            }
            //endregion

            //region HASH DA SENHA
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(paciente.getSenha().getBytes(), 0, paciente.getSenha().length());
            paciente.setSenha(new BigInteger(1,hash.digest()).toString(16));
            //endregion

            pacienteDao.save(paciente);
            DaoDto<Paciente> resultado = new DaoDto<>(true, "Aeeeee, vc ta dentro !!!");
            return new ResponseEntity(resultado, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ExceptionDto ex = new ExceptionDto(false, e.getMessage());
            return new ResponseEntity(ex, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/Paciente/Entrar")
    public ModelAndView entrar(String usr, String pwd, HttpSession session, HttpServletRequest request){
        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                ModelAndView pagina = new ModelAndView("user/painel");
                return pagina;
            }
        }
        ModelAndView pagina = null;
        ValidatorDto validacao = null;
        validacao = PacienteValidator.loginValidator(usr, pwd);
        if(!validacao.getOk()){
            pagina = new ModelAndView("PaginasErros/login");
            pagina.addObject("msg", validacao.getMsg());
            return pagina;
        }
        try{
            //region HASH DA SENHA
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(pwd.getBytes(), 0, pwd.length());
            pwd = new BigInteger(1,hash.digest()).toString(16);
            //endregion

            Paciente paciente = pacienteDao.findByEmailAndSenha(usr, pwd);
            if(paciente == null){
                pagina = new ModelAndView("PaginasErros/login");
                pagina.addObject("msg", "Não achei vc em, colocou o email e senha certinho ?");
                pagina.addObject("naoEncontrado", true);
                //region VISITANTE ACESSO
                if(session.getAttribute("visitante") == null){
                    UUID visitante = null;
                    VisitanteAcesso nVisitante = new VisitanteAcesso();
                    nVisitante.setNome(visitante.randomUUID().toString());
                    nVisitante.setPagina("Login");
                    nVisitante.setHoraAcesso(new Date());
                    nVisitante.setOrigem(request.getRemoteAddr());
                    session.setAttribute("visitante", visitante.randomUUID().toString());
                    session.setAttribute("origem", request.getRemoteAddr());
                    session.setAttribute("session", true);
                    visitanteAcessoDao.save(nVisitante);
                }else{
                    VisitanteAcesso nVisitante = new VisitanteAcesso();
                    nVisitante.setNome(session.getAttribute("visitante").toString());
                    nVisitante.setPagina("Login");
                    nVisitante.setHoraAcesso(new Date());
                    nVisitante.setOrigem(request.getRemoteAddr());
                    visitanteAcessoDao.save(nVisitante);
                }
                //endregion
                return pagina;
            }else{
                //region VISITANTE ACESSO
                if(session.getAttribute("visitante") == null){
                    UUID visitante = null;
                    VisitanteAcesso nVisitante = new VisitanteAcesso();
                    nVisitante.setNome(visitante.randomUUID().toString());
                    nVisitante.setPagina("Login");
                    nVisitante.setHoraAcesso(new Date());
                    nVisitante.setOrigem(request.getRemoteAddr());
                    session.setAttribute("visitante", visitante.randomUUID().toString());
                    session.setAttribute("origem", request.getRemoteAddr());
                    session.setAttribute("session", true);
                    visitanteAcessoDao.save(nVisitante);
                }else{
                    VisitanteAcesso nVisitante = new VisitanteAcesso();
                    nVisitante.setNome(session.getAttribute("visitante").toString());
                    nVisitante.setPagina("Login");
                    nVisitante.setHoraAcesso(new Date());
                    nVisitante.setOrigem(request.getRemoteAddr());
                    visitanteAcessoDao.save(nVisitante);
                }
                //endregion
                pagina = new ModelAndView("user/painel");
                pagina.addObject("msg", "Aeee logou");
                pagina.addObject("naoEncontrado", false);
                session.setAttribute("paciente", paciente);
                session.setAttribute("estaLogado", "SIM");
                return userHome(session);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO: GRAVAR SESSION
        //TODO: REDIRECIONAR
        return null;
    }

    @GetMapping(value = "/User")
    public ModelAndView userHome(HttpSession session){
        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                ModelAndView pagina = new ModelAndView("user/painel");
                pagina.addObject("totalConsultas", consultaDao.countAllByPaciente((Paciente) session.getAttribute("paciente")));
                pagina.addObject("totalExames", agendaExameDao.countAllByPaciente((Paciente) session.getAttribute("paciente")));
                pagina.addObject("totalMensagens", atendimentoDao.countAllByPacienteAndQuemEnviou((Paciente) session.getAttribute("paciente"), 2));
                pagina.addObject("ultimaConsulta", agendaDao.findAllByPacienteAndConfirmadoEqualsOrderByCodigoDesc((Paciente) session.getAttribute("paciente"), 1).get(0));
                //TODO: news
                //http://g1.globo.com/dynamo/ciencia-e-saude/rss2.xml
                try {
                    String url = "http://g1.globo.com/dynamo/ciencia-e-saude/rss2.xml";
                    URL feedUrl = new URL(url);
                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed feed = input.build(new XmlReader(feedUrl));
                    pagina.addObject("noticias", feed.getEntries());
//                    for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
//                        System.out.println(entry.getTitle());
//                        System.out.println(entry.getDescription());
//                        System.out.println(entry.getCategories());
//                        System.out.println(entry.getContents());
//                        System.out.println(entry.getComments());
//                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return pagina;
            }
        }else{
            ModelAndView pagina = new ModelAndView("PaginasErros/login");
            pagina.addObject("msg", "Ops, vc não entrou no sistema né ?");
            return pagina;
        }
        return new ModelAndView("user/painel");
    }

    @GetMapping(value = "/User/Exame")
    private ModelAndView showAgendaExame(HttpSession session){
        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                Paciente sessionPaciente = (Paciente) session.getAttribute("paciente");
                ModelAndView pagina = new ModelAndView("user/user-exames");
                pagina.addObject("exames", agendaExameDao.findAllByPacienteOrderByDataHoraDesc(sessionPaciente));
                return pagina;
            }
        }else{
            ModelAndView pagina = new ModelAndView("PaginasErros/login");
            pagina.addObject("msg", "Ops, vc não entrou no sistema né ?");
            return pagina;
        }
        ModelAndView pagina = new ModelAndView("PaginasErros/login");
        pagina.addObject("msg", "Ops, vc não entrou no sistema né ?");
        return pagina;
    }

    @GetMapping(value = "/User/Consulta")
    public ModelAndView listaPaciente(HttpSession session){
        Paciente paciente = (Paciente) session.getAttribute("paciente");
        ModelAndView pagina = new ModelAndView("user/atendimento");
        pagina.addObject("exames", consultaDao.findAllByPaciente(paciente));
        return pagina;
    }

    @GetMapping(value = "/User/Consulta/{codigo}")
    public ModelAndView userHome(@PathVariable("codigo") Long codigo, HttpSession session){
        ModelAndView pagina = new ModelAndView("user/atendimento-inicio");
        Consulta exame = consultaDao.findOne(codigo);

        if(exame == null){
            pagina.addObject("tamanho", null);
            pagina.addObject("appTipo", null);
            pagina.addObject("linkExame", null);
            pagina.addObject("descricao", null);
            pagina.addObject("urlArquivo", null);
        }else{
            pagina.addObject("tamanho", exame.getTamanho());
            pagina.addObject("appTipo", exame.getTipoArquivo());
            pagina.addObject("linkExame", exame.getUrlArquivo());
            pagina.addObject("descricao", exame.getDescricao());
            pagina.addObject("urlArquivo", exame.getUrlArquivo());
        }
        if(session.getAttribute("medico") != null){
            Medico nMedico = (Medico)session.getAttribute("medico");
            pagina.addObject("nivelAcesso", nMedico.getNivelAcesso());
        }else{
            pagina.addObject("nivelAcesso", 0);
        }
        pagina.addObject("medico", exame.getMedico());
        pagina.addObject("chat", atendimentoDao.findAllByMedicoAndPacienteAndConsulta(exame.getMedico(), exame.getPaciente(), exame));
        pagina.addObject("consulta", codigo);
        return pagina;
    }

    @GetMapping(value = "/User/Configuracao")
    public ModelAndView userConfig(HttpSession session){
        /* Verifica se ja esta logado */
        if(session.getAttribute("estaLogado") != null){
            if(session.getAttribute("estaLogado").toString().equals("SIM")){
                ModelAndView pagina = new ModelAndView("user/configuracao");
                pagina.addObject("paciente", session.getAttribute("paciente"));
                return pagina;
            }
        }else{
            ModelAndView pagina = new ModelAndView("PaginasErros/login");
            pagina.addObject("msg", "Ops, vc não entrou no sistema né ?");
            return pagina;
        }
        return new ModelAndView("user/configuracao");
    }

    @PostMapping(value = "/User/Update")
    public ResponseEntity userUpdate(Paciente paciente, HttpSession session){
        Paciente sessionPaciente = (Paciente) session.getAttribute("paciente");
        paciente.setCodigo(sessionPaciente.getCodigo());
        paciente.setSenha(sessionPaciente.getSenha());
        //region VALIDACOES
        ValidatorDto validacao = null;
        validacao = PacienteValidator.cadastroValidator(paciente);
        if(!validacao.getOk()){
            return new ResponseEntity(validacao, HttpStatus.OK);
        }
        //endregion
        try{
            pacienteDao.save(paciente);
            DaoDto<Paciente> resultado = new DaoDto<>(true, "Blz, tudo foi atualizado!");
            return new ResponseEntity(resultado, HttpStatus.OK);
        }catch (Exception e){
            ExceptionDto ex = new ExceptionDto(false, e.getMessage());
            return new ResponseEntity(ex, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/User/Password")
    public ResponseEntity userPasswordUpdate(String oldPassword, String newPassword, HttpSession session){
        Paciente sPaciente = (Paciente) session.getAttribute("paciente");
        try{
            //region HASH DA SENHA
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(oldPassword.getBytes(), 0, oldPassword.length());
            oldPassword = new BigInteger(1,hash.digest()).toString(16);
            //endregion
            if(oldPassword.equals(sPaciente.getSenha())){
                //region HASH DA SENHA
                hash = MessageDigest.getInstance("MD5");
                hash.update(newPassword.getBytes(), 0, newPassword.length());
                newPassword = new BigInteger(1,hash.digest()).toString(16);
                //endregion
                sPaciente.setSenha(newPassword);
                pacienteDao.save(sPaciente);
                DaoDto<Paciente> res = new DaoDto<>(true, "Senha atualizada com sucesso!");
                return new ResponseEntity(res, HttpStatus.OK);
            }else{
                DaoDto<Paciente> res = new DaoDto<>(false, "A senha antiga é diferente da senha atual!");
                return new ResponseEntity(res, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            DaoDto<Paciente> res = new DaoDto<>(false, "Falha de comunicação com o servidor!");
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

    @GetMapping("/Sair")
    public ModelAndView sair(HttpSession session){
        session.setAttribute("paciente", null);
        session.setAttribute("medico", null);
        session.setAttribute("estaLogado", "NAO");
        ModelAndView pagina = new ModelAndView("home");
        pagina.addObject("exames", exameDao.findAll());
        return pagina;
    }

    public String paginaRedirect(String pagina){
        return "";
    }

}
