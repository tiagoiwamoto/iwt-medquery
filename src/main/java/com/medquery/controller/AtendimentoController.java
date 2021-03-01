package com.medquery.controller;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.medquery.dao.*;
import com.medquery.dto.DaoDto;
import com.medquery.model.Atendimento;
import com.medquery.model.Consulta;
import com.medquery.model.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("all")
@Controller
public class AtendimentoController {

    String caminho = "D:\\Workspace\\Java\\MedQuery\\exames\\";
    String url = "http://localhost:8080/";


    @Autowired
    private PacienteDao pacienteDao;

    @Autowired
    private ConsultaDao exameDao;

    @Autowired
    private EspecializacaoDao especializacaoDao;

    @Autowired
    private DisponibilidadeDao disponibilidadeDao;

    @Autowired
    private AtendimentoDao atendimentoDao;

    @GetMapping(value = "/Atendimento/Medico")
    public ModelAndView listaMedico(HttpSession session){
        Medico medico = (Medico)session.getAttribute("medico");
        ModelAndView pagina = new ModelAndView("atendimento/lista");
        pagina.addObject("exames", exameDao.findAllByMedico(medico));
        return pagina;
    }

    @GetMapping(value = "/Atendimento/{codigo}")
    public ModelAndView home(@PathVariable("codigo") Long codigo, HttpSession session){
        ModelAndView pagina = new ModelAndView("atendimento/inicio");
        Consulta exame = exameDao.findOne(codigo);

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
        Medico nMedico = null;
        if(session.getAttribute("medico") != null){
            nMedico = (Medico)session.getAttribute("medico");
            pagina.addObject("nivelAcesso", nMedico.getNivelAcesso());
            session.setAttribute("codigoAtendimento", codigo);
        }else{
            pagina.addObject("nivelAcesso", 0);
        }
        pagina.addObject("paciente", exame.getPaciente());
        pagina.addObject("chat", atendimentoDao.findAllByMedicoAndPacienteAndConsulta(exame.getMedico(), exame.getPaciente(), exame));
        pagina.addObject("consulta", codigo);
        return pagina;
    }

    @PostMapping(value = "/Atendimento/Chat")
    public ResponseEntity novaMensagem(HttpSession session, Atendimento atendimento){
        if(session.getAttribute("medico") != null){
            atendimento.setQuemEnviou(1);
        }else{
            atendimento.setQuemEnviou(2);
        }
        atendimentoDao.save(atendimento);
        DaoDto<Atendimento> res = new DaoDto<>(true, "Enviada :]");
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping(value = "/Exame/{arquivo}")
    public @ResponseBody void downloadA(HttpServletResponse response, @PathVariable("arquivo") String arquivo) throws IOException {


        FilenameFilter filter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                return name.startsWith(arquivo);
            }
        };

        File file = new File(caminho);

        String[] children = file.list(filter);

        System.out.println(children[0]);

        file = new File(caminho + children[0]);


        InputStream in = new FileInputStream(file);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }


    @PostMapping(value = "/Exame/Novo")
    public ModelAndView novoExame(MultipartFile arquivo,
                                  String descricao,
                                  Long codigo,
                                  HttpSession session){
        ModelAndView pagina = null;

        Consulta exame = new Consulta();
        exame.setArquivo(arquivo);
        exame.setDescricao(descricao);
        exame.setPaciente(pacienteDao.findOne(codigo));

        if(exame.getArquivo() == null || exame.getArquivo().getSize() == 0){
            session.setAttribute("msg", "Escolha um exame né rsrs");
            pagina = new ModelAndView("exame/erro");
            return pagina;
        }



        exame.setTamanho(exame.getArquivo().getSize() / 1024);
        exame.setTipoArquivo(exame.getArquivo().getContentType());

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            byte[] bytes = exame.getArquivo().getBytes();
            String arquivoFinalNome = String.format("%s_%s", sdf.format(new Date()), exame.getArquivo().getOriginalFilename());

            /* Verifica se o arquivo jah existe na pasta */
            if(new File(arquivoFinalNome).exists()){
                session.setAttribute("msg", "Poxa vidaaa este arquvo já existe mané...");
                pagina = new ModelAndView("exame/erro");
                return pagina;
            }

            Path path = Paths.get(caminho + arquivoFinalNome);
            Files.write(path, bytes);
            exame.setUrlArquivo(arquivoFinalNome);
            exame.setMedico((Medico)session.getAttribute("medico"));
            exame.setPaciente(exame.getPaciente());
            exame.setCodigo((Long) session.getAttribute("codigoAtendimento"));
            exameDao.save(exame);
            return listaMedico(session);
        }catch (Exception e){
            pagina = new ModelAndView("exame/erro");
            return pagina;
        }


    }


}