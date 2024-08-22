package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProdutoRequestDTO {

    @JsonProperty
    private Produto produto;

    @JsonProperty
    private Integer quantidadeComprada;

    private BigDecimal precoTotal;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(Integer quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public BigDecimal getPrecoTotal() {
        return this.produto.getPreco().multiply(BigDecimal.valueOf(this.quantidadeComprada)).setScale(2, RoundingMode.HALF_UP);
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}
