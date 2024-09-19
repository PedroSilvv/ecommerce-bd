package com.mycompany.ecommerce.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long categoriaId;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private BigDecimal preco;
    private Integer quantidadeVendas = 0;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
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

    public Integer getQuantidadeVendas() {
        return quantidadeVendas;
    }

    public void setQuantidadeVendas(Integer quantidadeVendas) {
        this.quantidadeVendas = quantidadeVendas;
    }

    public void setNovaQuantidadeDeVendas(Integer quantidade) {
        this.quantidadeVendas += quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", categoriaId=" + categoriaId +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", quantidadeVendas=" + quantidadeVendas +
                '}';
    }
}
