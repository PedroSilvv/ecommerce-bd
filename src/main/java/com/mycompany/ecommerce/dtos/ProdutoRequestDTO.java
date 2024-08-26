package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Subcategoria;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProdutoRequestDTO {

    @JsonProperty
    private Long produto_id;

    @JsonProperty
    private Integer quantidadeComprada;

    private BigDecimal precoTotal;

    public Long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Long produto_id) {
        this.produto_id = produto_id;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public Integer getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(Integer quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}
