package com.mycompany.ecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name = "doc", length = 20)
    private String doc;

    @Column(name = "nome",nullable = false, length = 100 )
    private String nome;

    @Column(name = "user_role", nullable = false, length = 20)
    @JsonProperty
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password", nullable = false, length = 250)
    @JsonIgnore
    private String password;


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

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Role{
        ADMIN, DEFAULT
    }



}
