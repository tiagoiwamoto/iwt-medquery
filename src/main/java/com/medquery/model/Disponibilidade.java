package com.medquery.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/
@SuppressWarnings("all")
@Entity
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codigo;
    @Column(name = "Dia_Semana")
    private String diaSemana;
    @Column(name = "Hora_Disponivel")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date horaDisponivel;
    @Transient
    private String codigoDiaSemana;
    @ManyToOne
    private Medico medico;
    @Transient
    private Integer codigoSemana;
    @Transient
    private String nomeSemana;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }


    public String getDiaSemana() {
        if(diaSemana.equals("1")){
            this.codigoDiaSemana = "1";
        }
        if(diaSemana.equals("2")){
            this.codigoDiaSemana = "2";
        }
        if(diaSemana.equals("3")){
            this.codigoDiaSemana = "3";
        }
        if(diaSemana.equals("4")){
            this.codigoDiaSemana = "4";
        }
        if(diaSemana.equals("5")){
            this.codigoDiaSemana = "5";
        }
        if(diaSemana.equals("6")){
            this.codigoDiaSemana = "6";
        }
        return this.diaSemana;
    }

    public Integer getCodigoSemana() {
        return codigoSemana;
    }

    public void setCodigoSemana(Integer codigoSemana) {
        this.codigoSemana = codigoSemana;
    }

    public String getNomeSemana() {
        if(diaSemana.equals("1")){
            this.nomeSemana = "Segunda-Feira";
        }
        if(diaSemana.equals("2")){
            this.nomeSemana = "Terça-Feira";
        }
        if(diaSemana.equals("3")){
            this.nomeSemana = "Quarta-Feira";
        }
        if(diaSemana.equals("4")){
            this.nomeSemana = "Quinta-Feira";
        }
        if(diaSemana.equals("5")){
            this.nomeSemana = "Sexta-Feira";
        }
        if(diaSemana.equals("6")){
            this.nomeSemana = "Sábado";
        }
        return nomeSemana;
    }

    public void setNomeSemana(String nomeSemana) {
        this.nomeSemana = nomeSemana;
    }

    public String getCodigoDiaSemana() {
        return diaSemana;
    }

    public void setCodigoDiaSemana(String codigoDiaSemana) {
        this.codigoDiaSemana = codigoDiaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Date getHoraDisponivel() {
        return horaDisponivel;
    }

    public void setHoraDisponivel(Date horaDisponivel) {
        this.horaDisponivel = horaDisponivel;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Disponibilidade{");
        sb.append("codigo=").append(codigo);
        sb.append(", diaSemana='").append(diaSemana).append('\'');
        sb.append(", horaDisponivel=").append(horaDisponivel);
        sb.append(", codigoDiaSemana='").append(codigoDiaSemana).append('\'');
        sb.append(", medico=").append(medico);
        sb.append(", codigoSemana=").append(codigoSemana);
        sb.append(", nomeSemana='").append(nomeSemana).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
