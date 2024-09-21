package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;
import java.util.*;

public class CompraResponseDTO {

    @JsonProperty
    private String notaFiscal;

    @JsonProperty
    private String usuarioDoc;

    @JsonProperty
    private List<CompraProdutoResponseDTO> produtos = new ArrayList<>();

    @JsonProperty
    private Date dataCompra;

    @JsonProperty
    private BigDecimal precoTotal;

    public List<CompraProdutoResponseDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<CompraProdutoResponseDTO> produtos) {
        this.produtos = produtos;
    }

    public String getUsuarioDoc() {
        return usuarioDoc;
    }

    public void setUsuarioDoc(String usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}
