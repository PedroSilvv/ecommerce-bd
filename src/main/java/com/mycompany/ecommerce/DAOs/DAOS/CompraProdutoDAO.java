package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.CompraProduto;
import java.util.List;

public interface CompraProdutoDAO extends GenericDAO<CompraProduto> {

    List<CompraProduto> buscarPorCompra(String compraNotaFiscal) throws Exception;
}
