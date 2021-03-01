package com.medquery.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 13/05/2018 - 08:43
 */
@Entity
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "exame")
    private Set<AgendaExame> agendaExame;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<AgendaExame> getAgendaExame() {
        return agendaExame;
    }

    public void setAgendaExame(Set<AgendaExame> agendaExame) {
        this.agendaExame = agendaExame;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Exame{");
        sb.append("codigo=").append(codigo);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", agendaExame=").append(agendaExame);
        sb.append('}');
        return sb.toString();
    }
}
