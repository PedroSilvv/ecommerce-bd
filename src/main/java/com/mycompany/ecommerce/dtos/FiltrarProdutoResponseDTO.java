package com.mycompany.ecommerce.dtos;

import java.math.BigDecimal;
import java.util.List;

public class FiltrarProdutoResponseDTO {

    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private String categoriaNome;
    private String descricaoProduto;
    private List<String> subcategorias;

    public FiltrarProdutoResponseDTO(String nome, BigDecimal preco, Integer quantidade, String categoriaNome, String descricaoProduto, List<String> subcategorias) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoriaNome = categoriaNome;
        this.descricaoProduto = descricaoProduto;
        this.subcategorias = subcategorias;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public List<String> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<String> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
