package com.mycompany.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompraProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private String compraNotaFiscal;
    private Long produtoId;
    private Integer quantidadeItem;
    private BigDecimal precoTotalItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompraNotaFiscal() {
        return compraNotaFiscal;
    }

    public void setCompraNotaFiscal(String compraNotaFiscal) {
        this.compraNotaFiscal = compraNotaFiscal;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(Integer quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public BigDecimal getPrecoTotalItem() {
        return precoTotalItem;
    }

    public void setPrecoTotalItem(BigDecimal precoTotalItem) {
        this.precoTotalItem = precoTotalItem;
    }
}
