package com.medquery.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:23
 */
@Entity
@SuppressWarnings("all")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "cpf", length = 11)
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "telefone", length = 11)
    private Long telefone;
    @Column(name = "cep", length = 8)
    private Integer cep;
    @Column(name = "logradouro", length = 100)
    private String logradouro;
    @Column(name = "numero", length = 20)
    private String numero;
    @Column(name = "bairro", length = 50)
    private String bairro;
    @Column(name = "cidade", length = 50)
    private String cidade;
    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "sexo", length = 1)
    private Character sexo;
    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;
    @Column(name = "empregado", length = 1)
    private Character isEmpregado;
    @Column(name = "senha", length = 300)
    private String senha;
    @OneToMany(mappedBy = "paciente")
    private Set<Consulta> exame;
    @OneToMany(mappedBy = "paciente")
    private Set<Agenda> agenda;
    @OneToMany(mappedBy = "paciente")
    private Set<AgendaExame> agendaExame;

    public Set<Consulta> getExame() {
        return exame;
    }

    public void setExame(Set<Consulta> exame) {
        this.exame = exame;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Character getIsEmpregado() {
        return isEmpregado;
    }

    public void setIsEmpregado(Character isEmpregado) {
        this.isEmpregado = isEmpregado;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(Set<Agenda> agenda) {
        this.agenda = agenda;
    }

    public Set<AgendaExame> getAgendaExame() {
        return agendaExame;
    }

    public void setAgendaExame(Set<AgendaExame> agendaExame) {
        this.agendaExame = agendaExame;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Paciente{");
        sb.append("codigo=").append(codigo);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", cpf='").append(cpf).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", dataNascimento=").append(dataNascimento);
        sb.append(", telefone=").append(telefone);
        sb.append(", cep=").append(cep);
        sb.append(", logradouro='").append(logradouro).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", bairro='").append(bairro).append('\'');
        sb.append(", cidade='").append(cidade).append('\'');
        sb.append(", estado='").append(estado).append('\'');
        sb.append(", sexo=").append(sexo);
        sb.append(", estadoCivil='").append(estadoCivil).append('\'');
        sb.append(", isEmpregado=").append(isEmpregado);
        sb.append(", senha='").append(senha).append('\'');
        sb.append(", exame=").append(exame);
        sb.append(", agenda=").append(agenda);
        sb.append(", agendaExame=").append(agendaExame);
        sb.append('}');
        return sb.toString();
    }
}
