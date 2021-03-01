package com.medquery.dto;

import java.util.List;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:39
 */
@SuppressWarnings("all")
public class DaoDto<M> {

    private Boolean isOk;
    private String msg;
    private M model;
    private List<M> lista;

    /* Construtores */
    public DaoDto() {
    }

    public DaoDto(Boolean isOk, String msg) {
        this.isOk = isOk;
        this.msg = msg;
    }

    public DaoDto(Boolean isOk, String msg, M model) {
        this.isOk = isOk;
        this.msg = msg;
        this.model = model;
    }

    public DaoDto(Boolean isOk, String msg, List<M> lista) {
        this.isOk = isOk;
        this.msg = msg;
        this.lista = lista;
    }

    /* Getters and setters */
    public Boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public List<M> getLista() {
        return lista;
    }

    public void setLista(List<M> lista) {
        this.lista = lista;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DaoDto{");
        sb.append("isOk=").append(isOk);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", model=").append(model);
        sb.append(", lista=").append(lista);
        sb.append('}');
        return sb.toString();
    }
}
