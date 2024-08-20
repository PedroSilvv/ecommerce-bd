package com.mycompany.ecommerce.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "compra")
public class Compra implements Serializable {


    @Serial
    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_doc", nullable = false)
    private Usuario usuario;

    @Column(name = "status_compra", nullable = false)
    private Status status;

    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private Set<CompraProduto> compraProdutos;

    public Set<CompraProduto> getCompraProdutos() {
        return compraProdutos;
    }

    public void setCompraProdutos(Set<CompraProduto> compraProdutos) {
        this.compraProdutos = compraProdutos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public enum Status{
        PENDENTE, CANCELADA, CONCLUIDA
    }
}
