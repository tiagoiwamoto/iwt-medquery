package com.medquery.model;

import javax.persistence.*;
import java.util.Date;

/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/
@SuppressWarnings("all")
@Entity
public class Atendimento {

    //region ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @ManyToOne
    private Medico medico;
    @ManyToOne
    private Paciente paciente;
    @Column(name = "mensagem", length = 999)
    private String mensagem;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_criacao")
    private Date dataHoraCriacao;
    @Column(name = "quem_enviou", length = 1)
    private Integer quemEnviou;
    @ManyToOne
    private Consulta consulta;
    //endregion

    //region GETTERS AND SETTERS

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(Date dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public Integer getQuemEnviou() {
        return quemEnviou;
    }

    public void setQuemEnviou(Integer quemEnviou) {
        this.quemEnviou = quemEnviou;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    //endregion

    //region OVERRIDE

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Atendimento{");
        sb.append("codigo=").append(codigo);
        sb.append(", medico=").append(medico);
        sb.append(", paciente=").append(paciente);
        sb.append(", mensagem='").append(mensagem).append('\'');
        sb.append(", dataHoraCriacao=").append(dataHoraCriacao);
        sb.append(", quemEnviou=").append(quemEnviou);
        sb.append(", consulta=").append(consulta);
        sb.append('}');
        return sb.toString();
    }

    //endregion

}
