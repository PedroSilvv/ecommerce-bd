package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.Date;

public class CriarUsuarioDTO {

    private String doc;

    private String nome;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
