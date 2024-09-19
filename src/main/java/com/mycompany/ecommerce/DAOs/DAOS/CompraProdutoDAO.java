package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.jdbcModels.CompraProdutoJdbc;

import java.math.BigDecimal;
import java.util.List;

public interface CompraProdutoDAO {

    void inserirCompraProduto(
           String compraId,
           Long produtoId,
           Integer quantidadeItem,
           BigDecimal precoTotalItem
    ) throws Exception;

    List<CompraProdutoJdbc> buscarPorCompra(String compraNotaFiscal) throws Exception;
}
