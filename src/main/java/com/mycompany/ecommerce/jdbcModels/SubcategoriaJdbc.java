package com.mycompany.ecommerce.jdbcModels;

import java.io.Serializable;

public class SubcategoriaJdbc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private Long categoriaId; // Usado para o relacionamento com Categoria

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

    // Getter e Setter para categoriaId
    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
