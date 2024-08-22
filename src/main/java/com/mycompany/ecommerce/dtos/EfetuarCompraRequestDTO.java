package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Produto;
import com.mycompany.ecommerce.models.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

public class EfetuarCompraRequestDTO {


    @JsonProperty
    private Usuario usuario;

    @JsonProperty
    private Set<ProdutoRequestDTO> produtos;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<ProdutoRequestDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoRequestDTO> produtos) {
        this.produtos = produtos;
    }
}
