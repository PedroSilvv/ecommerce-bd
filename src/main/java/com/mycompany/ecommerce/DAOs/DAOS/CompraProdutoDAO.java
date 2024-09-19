package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.CompraProduto;
import java.util.List;

public interface CompraProdutoDAO extends GenericDAO<CompraProduto> {

//    void inserirCompraProduto(
//           String compraId,
//           Long produtoId,
//           Integer quantidadeItem,
//           BigDecimal precoTotalItem
//    ) throws Exception;

    List<CompraProduto> buscarPorCompra(String compraNotaFiscal) throws Exception;
}
