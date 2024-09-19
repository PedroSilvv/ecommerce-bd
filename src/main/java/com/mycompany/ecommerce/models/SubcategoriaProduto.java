package com.mycompany.ecommerce.models;

import java.io.Serializable;

public class SubcategoriaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long subcategoriaId; // Referência para a tabela Subcategoria
    private Long produtoId;      // Referência para a tabela Produto

    // Getter e Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para subcategoriaId
    public Long getSubcategoriaId() {
        return subcategoriaId;
    }

    public void setSubcategoriaId(Long subcategoriaId) {
        this.subcategoriaId = subcategoriaId;
    }

    // Getter e Setter para produtoId
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
}
