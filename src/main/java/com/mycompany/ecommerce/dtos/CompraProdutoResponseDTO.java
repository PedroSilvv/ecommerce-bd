package com.mycompany.ecommerce.dtos;

public class CompraProdutoResponseDTO {

    private Integer produtoId ;
    private String produtoNome;
    private Integer quantidade;

    public CompraProdutoResponseDTO(Integer produtoId, String produtoNome, Integer quantidade) {
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
