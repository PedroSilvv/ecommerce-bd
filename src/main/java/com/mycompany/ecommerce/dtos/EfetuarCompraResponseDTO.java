package com.mycompany.ecommerce.dtos;

import com.mycompany.ecommerce.models.Compra;
import com.mycompany.ecommerce.models.CompraProduto;
import com.mycompany.ecommerce.models.Usuario;

import java.util.Set;

public class EfetuarCompraResponseDTO {

    private Compra compra;
    private Usuario usuario;

    private Set<CompraProduto> produtos;


}
