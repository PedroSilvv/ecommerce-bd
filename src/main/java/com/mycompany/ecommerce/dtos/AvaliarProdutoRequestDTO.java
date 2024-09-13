package com.mycompany.ecommerce.dtos;

public class AvaliarProdutoRequestDTO {

    private String usuarioDoc;
    private Integer nota;

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
