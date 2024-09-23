package com.mycompany.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private String nome;

    // Getter e Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
