package com.medquery.model;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("all")
@Entity
public class Agenda {

    //region ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "dia_consulta")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date diaConsulta;
    @Column(name = "hora_consulta")
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern="HH:mm:ss")
    private Date horaConsulta;
    @Column(name = "confirmado", length = 1)
    private Integer confirmado;
    @Column(name = "cancelado")
    private Integer cancelado;
    @ManyToOne
    private Medico medico;
    @ManyToOne
    private Paciente paciente;
    //endregion

    //region GETTERS AND SETTERS


    public Integer getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Integer confirmado) {
        this.confirmado = confirmado;
    }

    public Integer getCancelado() {
        return cancelado;
    }

    public void setCancelado(Integer cancelado) {
        this.cancelado = cancelado;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDiaConsulta() {
        return diaConsulta;
    }

    public void setDiaConsulta(Date diaConsulta) {
        this.diaConsulta = diaConsulta;
    }

    public Date getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(Date horaConsulta) {
        this.horaConsulta = horaConsulta;
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

    //endregion

    //region OVERRIDE

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Agenda{");
        sb.append("codigo=").append(codigo);
        sb.append(", diaConsulta=").append(diaConsulta);
        sb.append(", horaConsulta=").append(horaConsulta);
        sb.append(", confirmado=").append(confirmado);
        sb.append(", cancelado=").append(cancelado);
        sb.append(", medico=").append(medico);
        sb.append(", paciente=").append(paciente);
        sb.append('}');
        return sb.toString();
    }

    //endregion

}
