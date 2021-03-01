package com.medquery.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 13/05/2018 - 16:54
 */
@Entity
public class AgendaExame {

    //region ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "status")
    private Integer status;
    @Column(name = "data_hora")
    private Date dataHora;
    @ManyToOne
    private Exame exame;
    @ManyToOne
    private Paciente paciente;
    //endregion

    //region GETTERS AND SETTERS

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
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
        final StringBuilder sb = new StringBuilder("AgendaExame{");
        sb.append("codigo=").append(codigo);
        sb.append(", status=").append(status);
        sb.append(", dataHora=").append(dataHora);
        sb.append(", exame=").append(exame);
        sb.append(", paciente=").append(paciente);
        sb.append('}');
        return sb.toString();
    }

    //endregion

}
