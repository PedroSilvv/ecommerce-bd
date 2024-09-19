package com.mycompany.ecommerce.jdbcModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AvaliacaoJdbc implements Serializable {

    private Long id;
    private Long produtoId;
    private String usuarioDoc;
    private Integer nota;
    private Date dataAvaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
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

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    @Override
    public String toString() {
        return "AvaliacaoJdbc{" +
                "id=" + id +
                ", produtoId=" + produtoId +
                ", usuarioDoc='" + usuarioDoc + '\'' +
                ", nota=" + nota +
                ", dataAvaliacao=" + dataAvaliacao +
                '}';
    }
}
