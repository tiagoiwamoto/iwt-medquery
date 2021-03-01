package com.medquery.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 13/04/2018 - 21:32
 */
@Entity
public class VisitanteAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo")
    private Long codigo;
    @Column(name = "Origem")
    private String origem;
    @Column(name = "Nome")
    private String nome;
    @Column(name = "Pagina")
    private String pagina;
    @Column(name = "Hora_Acesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaAcesso;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public Date getHoraAcesso() {
        return horaAcesso;
    }

    public void setHoraAcesso(Date horaAcesso) {
        this.horaAcesso = horaAcesso;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VisitanteAcesso{");
        sb.append("codigo=").append(codigo);
        sb.append(", origem='").append(origem).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", pagina='").append(pagina).append('\'');
        sb.append(", horaAcesso=").append(horaAcesso);
        sb.append('}');
        return sb.toString();
    }
}
