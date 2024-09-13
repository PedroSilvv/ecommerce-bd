package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import jakarta.persistence.*;

import java.util.Date;

public class AvaliarProdutoResponseDTO {

    private Long id;
    private Produto produto;
    private String usuarioDoc;
    private Integer nota;
    private Date dataAvaliacao;

    public AvaliarProdutoResponseDTO(Long id, Produto produto, String usuarioDoc, Integer nota, Date dataAvaliacao) {
        this.id = id;
        this.produto = produto;
        this.usuarioDoc = usuarioDoc;
        this.nota = nota;
        this.dataAvaliacao = dataAvaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getUsuarioDoc() {
        return usuarioDoc;
    }

    public void setUsuarioDoc(String usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
