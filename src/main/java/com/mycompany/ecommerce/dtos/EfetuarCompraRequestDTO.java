package com.mycompany.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class EfetuarCompraRequestDTO {


    @JsonProperty
    private String usuarioDoc;

    @JsonProperty
    private Set<ProdutoRequestDTO> produtos;

    public String getUsuarioDoc() {
        return usuarioDoc;
    }

    public void setUsuarioDoc(String usuarioDoc) {
        this.usuarioDoc = usuarioDoc;
    }

    public Set<ProdutoRequestDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoRequestDTO> produtos) {
        this.produtos = produtos;
    }
}
