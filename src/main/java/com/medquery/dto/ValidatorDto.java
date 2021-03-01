package com.medquery.dto;

/**
 * Tiago Iwamoto - tiago.iwamoto@gmail.com
 * Criado em: 12/04/2018 - 21:41
 */
@SuppressWarnings("all")
public class ValidatorDto {

    private Boolean isOk;
    private String msg;

    /* Construtores */

    public ValidatorDto() {
    }

    public ValidatorDto(Boolean isOk, String msg) {
        this.isOk = isOk;
        this.msg = msg;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidatorDto{");
        sb.append("isOk=").append(isOk);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
