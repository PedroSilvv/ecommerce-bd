package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Usuario;

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
}
