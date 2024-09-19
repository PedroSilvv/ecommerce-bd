package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.jdbcModels.CompraProdutoJdbc;
import com.mycompany.ecommerce.models.CompraProduto;

import java.math.BigDecimal;
import java.util.List;

public interface CompraProdutoDAO extends GenericDAO<CompraProdutoJdbc> {

//    void inserirCompraProduto(
//           String compraId,
//           Long produtoId,
//           Integer quantidadeItem,
//           BigDecimal precoTotalItem
//    ) throws Exception;

    List<CompraProdutoJdbc> buscarPorCompra(String compraNotaFiscal) throws Exception;
}
