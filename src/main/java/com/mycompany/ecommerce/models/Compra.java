package com.mycompany.ecommerce.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    private String notaFiscal;
    private String usuarioDoc;
    private Status status;
    private BigDecimal precoTotal;
    private Date dataCompra;

    private Set<CompraProduto> compraProdutos = new HashSet<>();

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getUsuarioDoc() {
        return usuarioDoc;
    }

    public void setUsuarioDoc(String usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusJdbc(String status){
        this.status = Status.valueOf(status);
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Set<CompraProduto> getCompraProdutos() {
        return compraProdutos;
    }

    public void setCompraProdutos(Set<CompraProduto> compraProdutos) {
        this.compraProdutos = compraProdutos;
    }

    public enum Status {
        PENDENTE, CANCELADA, CONCLUIDA
    }

    @Override
    public String toString() {
        return "Compra{" +
                "notaFiscal='" + notaFiscal + '\'' +
                ", usuarioId=" + usuarioDoc +
                ", status=" + status +
                ", precoTotal=" + precoTotal +
                ", compraProdutos=" + compraProdutos +
                ", dataCompra=" + dataCompra +
                '}';
    }
}

