package com.medquery.model;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("all")
@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "descricao", length = 150)
    private String descricao;

    @Column(name = "url_arquivo", length = 255)
    private String urlArquivo;

    @Column(name = "tamanho")
    private Long tamanho;

    @Column(name = "tipo_aquivo")
    private String tipoArquivo;

    @Column(name = "eh_compartilhado", length = 1)
    private Integer isCompartilhado;

    @Column(name = "data_inicio_compartilhamento")
    @Temporal(TemporalType.DATE)
    private Date dataInicioCompartilhamento;

    @Transient
    private Long pacienteCodigo;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;

    @OneToMany(mappedBy = "consulta")
    private Set<Atendimento> atendimentos;

    @Transient
    private MultipartFile arquivo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public Integer getIsCompartilhado() {
        return isCompartilhado;
    }

    public void setIsCompartilhado(Integer isCompartilhado) {
        this.isCompartilhado = isCompartilhado;
    }

    public Date getDataInicioCompartilhamento() {
        return dataInicioCompartilhamento;
    }

    public void setDataInicioCompartilhamento(Date dataInicioCompartilhamento) {
        this.dataInicioCompartilhamento = dataInicioCompartilhamento;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    public Long getPacienteCodigo() {
        return pacienteCodigo;
    }

    public void setPacienteCodigo(Long pacienteCodigo) {
        this.pacienteCodigo = pacienteCodigo;
    }

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }
}
