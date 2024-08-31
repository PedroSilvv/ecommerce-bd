package com.mycompany.ecommerce.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compra")
public class Compra implements Serializable {


    @Id
    @Column(name = "nota_fiscal")
    private String notaFiscal;

    @ManyToOne
    @JoinColumn(name = "usuario_doc", nullable = false)
    private Usuario usuario;

    @Column(name = "status_compra", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private Set<CompraProduto> compraProdutos = new HashSet<>();

    @Column(name = "data_compra", nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCompra;

    public Set<CompraProduto> getCompraProdutos() {
        return compraProdutos;
    }

    public void setCompraProdutos(Set<CompraProduto> compraProdutos) {
        this.compraProdutos = compraProdutos;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public enum Status{
        PENDENTE, CANCELADA, CONCLUIDA
    }


}
