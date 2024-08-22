package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Set;

public class CadastrarProdutoRequestDTO {

    @JsonProperty
    private Categoria categoria;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String descricao;

    @JsonProperty
    private Integer quantidade;

    @JsonProperty
    private BigDecimal preco;

    @JsonProperty
    private Set<String> subcategorias;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Set<String> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(Set<String> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
