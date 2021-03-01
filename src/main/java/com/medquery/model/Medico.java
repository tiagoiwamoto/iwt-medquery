package com.medquery.model;
/*
  Created by: Tiago Iwamoto
  Contact: tiago.iwamoto@gmail.com
  System Analyst  
*/

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("all")
@Entity
public class Medico {

    //region ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo")
    private Long codigo;
    @Column(name = "Cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "Crm", nullable = false)
    private String crm;
    @Column(name = "Rg", nullable = false)
    private String rg;
    @Column(name = "Sexo", length = 1)
    private String sexo;
    @Column(name = "Nome_Completo", nullable = false, length = 150)
    private String nomeCompleto;
    @Column(name = "DataNascimento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    @Column(name = "Email")
    private String email;
    @Column(name = "Telefone_Residencial")
    private Long telefoneResidencial;
    @Column(name = "Telefone_Celular")
    private Long telefoneCelular;
    @Column(name = "Telefone_Recado")
    private Long telefoneRecado;
    @Column(name = "Nome_Mae")
    private String nomeMae;
    @Column(name = "Nome_Pai")
    private String nomePai;
    @Column(name = "Nivel_Acesso", length = 1)
    private Integer nivelAcesso;
    @Column(name = "Nome_Usuario", nullable = false)
    private String usr;
    @Column(name = "Senha_Usuario", nullable = false)
    private String pwd;
    @Column(name = "Recaptcha")
    private String recaptcha;
    @ManyToOne
    private Especializacao especializacao;
    @OneToMany(mappedBy = "medico")
    @OrderBy("diaSemana ASC")
    private Set<Disponibilidade> disponibilidade;
    @OneToMany(mappedBy = "medico")
    private Set<Consulta> exames;
    @OneToMany(mappedBy = "medico")
    private Set<Agenda> agenda;
    //endregion

    //region GETTERS AND SETTERS
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(Long telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public Long getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Long telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Long getTelefoneRecado() {
        return telefoneRecado;
    }

    public void setTelefoneRecado(Long telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Integer getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Integer nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(String recaptcha) {
        this.recaptcha = recaptcha;
    }

    public Especializacao getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(Especializacao especializacao) {
        this.especializacao = especializacao;
    }

    public Set<Disponibilidade> getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Set<Disponibilidade> disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Set<Consulta> getExames() {
        return exames;
    }

    public void setExames(Set<Consulta> exames) {
        this.exames = exames;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(Set<Agenda> agenda) {
        this.agenda = agenda;
    }

    //endregion

    //region OVERRIDE
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medico{");
        sb.append("codigo=").append(codigo);
        sb.append(", cpf='").append(cpf).append('\'');
        sb.append(", crm='").append(crm).append('\'');
        sb.append(", rg='").append(rg).append('\'');
        sb.append(", sexo='").append(sexo).append('\'');
        sb.append(", nomeCompleto='").append(nomeCompleto).append('\'');
        sb.append(", dataNascimento=").append(dataNascimento);
        sb.append(", email='").append(email).append('\'');
        sb.append(", telefoneResidencial=").append(telefoneResidencial);
        sb.append(", telefoneCelular=").append(telefoneCelular);
        sb.append(", telefoneRecado=").append(telefoneRecado);
        sb.append(", nomeMae='").append(nomeMae).append('\'');
        sb.append(", nomePai='").append(nomePai).append('\'');
        sb.append(", nivelAcesso=").append(nivelAcesso);
        sb.append(", usr='").append(usr).append('\'');
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append(", recaptcha='").append(recaptcha).append('\'');
        sb.append(", especializacao=").append(especializacao);
        sb.append(", disponibilidade=").append(disponibilidade);
        sb.append(", exames=").append(exames);
        sb.append(", agenda=").append(agenda);
        sb.append('}');
        return sb.toString();
    }
    //endregion
}
