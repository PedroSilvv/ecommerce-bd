package com.mycompany.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "compra_item")
public class CompraProduto implements Serializable {

    @Serial
    private static final long serialVersionUID= 1L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "compra_nota_fiscal", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    @JsonProperty
    private Produto produto;

    @Column(name = "quantidade_item", nullable = false)
    @JsonProperty
    private Integer quantidadeItem;

    @Column(name = "preco_total_item")
    @JsonProperty
    private BigDecimal precoTotalItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
