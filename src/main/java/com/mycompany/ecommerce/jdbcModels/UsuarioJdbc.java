package com.mycompany.ecommerce.jdbcModels;

import java.io.Serializable;
import java.util.Date;

public class UsuarioJdbc implements Serializable {

    private static final long serialVersionUID = 1L;

    private String doc;
    private String nome;
    private Role role;
    private String password;
    private Date dataNasc;

    // Getters and Setters

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Role getRole() {
        return role;
    }

    public void setSingleRole(String role){
        this.role = Role.valueOf(role);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    // Enum Role for user roles
    public enum Role {
        ADMIN,
        DEFAULT
    }
}
