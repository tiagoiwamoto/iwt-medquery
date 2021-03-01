package com.medquery.model;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("all")
@Entity
public class Especializacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo")
    private Long codigo;
    @Column(name = "Nome")
    private String nome;
    @Column(name = "Palavra_Chave")
    private String palavraChave;
    @Column(name = "Descricao", length = 255)
    private String descricao;
    @Column(name = "Data_Criacao")
    private Date dataCriacao;
    @Column(name = "Data_Alteracao")
    private Date dataAlteracao;
    @Column(name = "Usuario_Criacao")
    private String usuarioCriacao;
    @Column(name = "Usuario_Modificacao")
    private String usuarioModificacao;
    @OneToMany(mappedBy = "especializacao")
    private Set<Medico> medicos;

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

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public String getUsuarioModificacao() {
        return usuarioModificacao;
    }

    public void setUsuarioModificacao(String usuarioModificacao) {
        this.usuarioModificacao = usuarioModificacao;
    }

    public Set<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(Set<Medico> medicos) {
        this.medicos = medicos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Especializacao{");
        sb.append("codigo=").append(codigo);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", palavraChave='").append(palavraChave).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", dataCriacao=").append(dataCriacao);
        sb.append(", dataAlteracao=").append(dataAlteracao);
        sb.append(", usuarioCriacao='").append(usuarioCriacao).append('\'');
        sb.append(", usuarioModificacao='").append(usuarioModificacao).append('\'');
        sb.append(", medicos=").append(medicos);
        sb.append('}');
        return sb.toString();
    }
}
