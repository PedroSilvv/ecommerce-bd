package com.mycompany.ecommerce.DAOs.DAOS;

import com.mycompany.ecommerce.DAOs.generic.GenericDAO;
import com.mycompany.ecommerce.models.Compra;

import java.math.BigDecimal;
import java.util.List;

public interface CompraDAO extends GenericDAO<Compra> {

    List<Compra> buscarPorUsuario(String usuarioDoc) throws Exception;

//    void inserirCompra(
//            String nota_fiscal,
//            String usuarioDoc,
//            String statusCompra,
//            BigDecimal precoTotal,
//            Date dataCompra
//    ) throws Exception;

    void atualizarPrecoTotal(BigDecimal precoTotal, String notaFiscal) throws Exception;

}
